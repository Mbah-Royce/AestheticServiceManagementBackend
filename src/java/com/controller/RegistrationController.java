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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("registrationcontroller/user/")
public class RegistrationController {

    private String generateOtp() {
        Random rnd = new Random();
        return String.valueOf(rnd.nextInt(999999));
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserModel user) throws Exception {
        JsonObject json;
        int statusCode = 201;
        String email = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String selectStm = "select email from users where email = ?";
            PreparedStatement selectPrSmt = com.connexion.Dbconnexion.seconnecter().prepareStatement(selectStm);
            selectPrSmt.setString(1, user.getEmail());
            ResultSet rs = selectPrSmt.executeQuery();
            while (rs.next()) {
                email = rs.getString("email");
            }
            if (email.isEmpty()) {
                String pass = com.funcservices.HashCreator.createPasswordHashWithSalt(user.getPassword(), user.getEmail().getBytes());
                String sqlStatment = "Insert into users (firstname,lastname,email,password,state,gender,usercreatedat,userupdateat,phone) " + "values (?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = com.connexion.Dbconnexion.seconnecter().prepareStatement(sqlStatment);
                pstmt.setString(1, user.getFirstname());
                pstmt.setString(2, user.getLastname());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, pass);
                pstmt.setString(5, "active");
                pstmt.setString(6, user.getGender());
                pstmt.setTimestamp(7, timestamp);
                pstmt.setTimestamp(8, timestamp);
                pstmt.setString(9, user.getPhone());
                pstmt.executeUpdate();
                String number = generateOtp();
                com.connexion.RedisConnecxion.redisConnect().set(user.getEmail(), number);
                com.funcservices.Mailer.send("from@gmail.com", user.getEmail(), "Registration Code", "Your OTP veficication code is " +number);
                selectStm = "select * from users where email =?";
                pstmt = com.connexion.Dbconnexion.seconnecter().prepareStatement(selectStm);
                pstmt.setString(1, user.getEmail());
                rs = pstmt.executeQuery();
                rs.next();
                json = Json.createObjectBuilder()
                        .add("name", user.getFirstname()+" "+user.getLastname())
                        .add("email",user.getEmail())
                        .add("userid", rs.getString("userid"))
                        .build();
                statusCode = 201;
            } else {
                json = Json.createObjectBuilder().add("error", "Emial already taken").build();
                statusCode = 422;
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();

    }

    @GET
    @Path("resendOtp/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response resendOtp(@PathParam("userid") int userid) throws SQLException {
        String email = "";
        int statusCode = 200;
        JsonObject json;
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            String stm = "select email from users where userid = ?";
            PreparedStatement pstm = con.prepareStatement(stm);
            pstm.setInt(1, userid);
            ResultSet rs = pstm.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                email = rs.getString("email");
                String newCode = generateOtp();
                com.connexion.RedisConnecxion.redisConnect().set(email, newCode);
                String msg = "Your OPT code is" + newCode;
                com.funcservices.Mailer.send("tolosricardos@gmail.com", email, "Registration Code", msg);
                json = Json.createObjectBuilder().add("error", "OPT code sent successfully").build();
                statusCode = 200;
            } else {
                json = Json.createObjectBuilder().add("error", "User Not Found").build();
                statusCode = 404;
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }
}
