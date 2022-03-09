/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.jwttoken.JavaJWT;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mbah Royce
 */
@Path("logincontroller/")
public class LoginController {

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonObject json) {
        int statusCode = 200;
        String pass = com.funcservices.HashCreator.createPasswordHashWithSalt(json.getString("password", "null"), json.getString("email", null).getBytes());
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            String sql = "Select * from users where email = ? and password = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, json.getString("email", null));
            pstm.setString(2, pass);
            ResultSet rs = pstm.executeQuery();
            if (!rs.isBeforeFirst()) {
                json = Json.createObjectBuilder().add("error", "Password or Email incorrect").build();
                statusCode = 422;
            } else {
                rs.next();
                if (rs.getTimestamp("verifiedat") != null) {
                    String token = JavaJWT.createJWT(rs.getString("userid"), rs.getString("email"), "auth", 0);
                    json = Json.createObjectBuilder().add("token", token)
                            .add("userid", rs.getString("userid"))
                            .add("email", rs.getString("email"))
                            .add("first_name", rs.getString("firstname"))
                            .add("last_name", rs.getString("lastname")).build();
                }else{
                    json = Json.createObjectBuilder().add("message", "Please verify your account").build();
                    statusCode = 403;
                }
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }
}
