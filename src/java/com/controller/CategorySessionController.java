/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.CategorySessionModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("auth/categorycessioncontroller/")
public class CategorySessionController {
    
    @GET
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@PathParam("sessionId") int sessionId) {
        JsonObject json = null;
        String sqlStmt = "select * from sessions order by sessionid";
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("sessionid", rs.getInt("sessionid"))
                        .add("sessionname", rs.getString("sessionname"))
                        .add("starttime", rs.getString("starttime"))
                        .add("endtime", rs.getString("endtime"))
                        .add("sessioncreateddat", rs.getString("sessionupdatedat"))
                        .add("sessionupdatedat", rs.getString("sessioncreateddat"))
                        .add("categoryid", rs.getInt("categoryid"))
                        .build();
                 builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
    }

    @POST
    @Path("session")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response create(CategorySessionModel categorySession) {
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "insert into sessions (categoryid,sessionname,starttime,endtime,sessioncreateddat,sessionupdatedat) values (?,?,?,?,?,?)";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, categorySession.getCategoryid());
            pstm.setString(2, categorySession.getSessionname());
            pstm.setString(3, categorySession.getStarttime());
            pstm.setString(4, categorySession.getEndtime());
            pstm.setTimestamp(5, timestamp);
            pstm.setTimestamp(6, timestamp);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Session Created Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error","An error Occured" + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }
    
    @GET
    @Path("session/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("sessionId") int sessionId) {
        JsonObject json = null;
        String sqlStmt = "select * from sessions  where sessionid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, sessionId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("sessionid", rs.getInt("sessionid"))
                        .add("name", rs.getString("sessionname"))
                        .add("starttime", rs.getString("starttime"))
                        .add("endtime", rs.getString("endtime"))
                        .add("sessioncreateddat", rs.getString("sessionupdatedat"))
                        .add("sessionupdatedat", rs.getString("sessioncreateddat"))
                        .add("categoryid", rs.getInt("categoryid"))
                        .build();
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    @PUT
    @Path("session/{sessionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response update(@PathParam("sessionId") int sessionId,CategorySessionModel categorySession) {
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "update sessions set sessionname = ?,starttime = ?,endtime = ?, sessionupdatedat = ? where sessionid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setString(1, categorySession.getSessionname());
            pstm.setString(2, categorySession.getStarttime());
            pstm.setString(3, categorySession.getEndtime());
            pstm.setTimestamp(4, timestamp);
            pstm.setInt(5, sessionId);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Session Updated Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error","An error Occured" + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    @DELETE
    @Path("session/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("catId") int catId) {
        JsonObject json;
        String sqlStmt = "delete from sessions where sessionid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, catId);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Session Deleted Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    @GET
    @Path("category/sessions/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response session(@PathParam("catId") int catId) throws SQLException{
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        String sqlStmt =  "select * from sessions  where categoryid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, catId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("sessionid", rs.getInt("sessionid"))
                        .add("name", rs.getString("sessionname"))
                        .add("starttime", rs.getString("starttime"))
                        .add("endtime", rs.getString("endtime"))
                        .add("sessioncreateddat", rs.getString("sessionupdatedat"))
                        .add("sessionupdatedat", rs.getString("sessioncreateddat"))
                        .build();
                builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
    }

}
