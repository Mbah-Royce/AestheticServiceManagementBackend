/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.jwttoken.JavaJWT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mbah Royce
 */
@Path("/user/VerifyOtpController")
public class VerifyOtpController {

    @POST
    @Path("/verify/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify(@PathParam("userid") int userid, JsonObject otpCode) throws SQLException {
        String key = "";
        JsonObject json;
        int statusCode = 200;
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            String sqlStmt = "select * from users where userid = ?" ;
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, userid);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            key = rs.getString("email");
            if (!key.isEmpty()) {
                String value = com.connexion.RedisConnecxion.redisConnect().get(key);
                if (!value.isEmpty() && value.equals(otpCode.getString("optCode","null"))) {
                    com.connexion.RedisConnecxion.redisConnect().del(key);
                    String token = JavaJWT.createJWT(String.valueOf(userid), key, "auth",0);
                    json = Json.createObjectBuilder().add("token", token)
                            .add("userid", rs.getString("userid"))
                            .add("email", rs.getString("email"))
                            .add("first_name", rs.getString("firstname"))
                            .add("last_name", rs.getString("lastname")).build();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                sqlStmt = "update users set verifiedat = ? where userid = ?" ;
                pstm = con.prepareStatement(sqlStmt);
                pstm.setTimestamp(1, timestamp);
                pstm.setInt(2, userid);
                pstm.executeUpdate();
                } else {
                    json = Json.createObjectBuilder().add("message", "Code not correct").build();
                    statusCode = 400;
                }
            } else {
                json = Json.createObjectBuilder().add("message", "User not found").build();
                statusCode = 404;
            }

        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", "An error occured ensure optcode was sent to email " + e.getMessage() ).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }
}

//        Response.c
//        else{
//            ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
//            Response response = builder.build();
//            throw new WebApplicationException(response);
//        }
//        return "not found";
