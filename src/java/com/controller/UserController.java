/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mbah Royce
 */
@Path("auth/usercontroller")
public class UserController {

    /**
     * List all users
     *
     * @return
     */
    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        int statusCode = 200;
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement("select * from users order by userid");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("user_id", rs.getInt("userid"))
                        .add("first_name", rs.getString("firstname"))
                        .add("last_name", rs.getString("lastname"))
                        .add("email", rs.getString("email"))
                        .add("gender", rs.getString("gender"))
                        .add("phone", rs.getString("phone"))
                        .add("state", rs.getString("state"))
                        .add("verifiedat", rs.getString("verifiedat"))
                        .add("usercreatedat", rs.getString("usercreatedat"))
                        .add("userupdateat", rs.getString("userupdateat"))
                        .build();
                builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(statusCode).entity(builder.build().toString()).build();
    }

    /**
     * Show user details
     *
     * @param userid
     * @return
     */
    @GET
    @Path("user/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("userid") int userid) {
        int statusCode = 200;
        JsonObject json = null;
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement("select * from users where userid = ?");
            pstm.setInt(1, userid);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("user_id", rs.getInt("userid"))
                        .add("first_name", rs.getString("firstname"))
                        .add("last_name", rs.getString("lastname"))
                        .add("email", rs.getString("email"))
                        .add("servicecreatedat", rs.getString("gender"))
                        .add("gender", rs.getString("phone"))
                        .add("state", rs.getString("state"))
                        .add("usercreatedat", rs.getString("usercreatedat"))
                        .add("userupdateat", rs.getString("userupdateat"))
                        .build();
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }

    /**
     * Update user info
     *
     * @param userid
     * @param user
     * @return
     */
    @PUT
    @Path("user/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("userid") int userid, UserModel user) {
        int statusCode = 200;
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "update users set firstname = ?,lastname = ?,email = ?,gender = ?,userupdateat = ?,phone = ? where userid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstmt = con.prepareStatement(sqlStmt);
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getLastname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getGender());
            pstmt.setTimestamp(5, timestamp);
            pstmt.setString(6, user.getPhone());
            pstmt.setInt(7, userid);
            pstmt.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Information Updated Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }

    /**
     * Delete a user
     *
     * @param userid
     * @return
     */
    @DELETE
    @Path("user/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("userid") int userid) {
        return null;
    }
}
