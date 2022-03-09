/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.CategoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
@Path("auth/categorycontroller/")
public class CategoryController {
    
    @POST
    @Path("category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response create(CategoryModel categoryModel){
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "insert into categories (serviceid,categoryname,price,duration,categorystate,categorycreatedat,categoryupdateat,categorydescription) values (?,?,?,?,?,?,?,?)";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, categoryModel.getServiceid());
            pstm.setString(2, categoryModel.getCategoryname());
            pstm.setDouble(3, categoryModel.getPrice());
            pstm.setInt(4, categoryModel.getDuration());
            pstm.setString(5, categoryModel.getCategorystate());
            pstm.setTimestamp(6, timestamp);
            pstm.setTimestamp(7, timestamp);
            pstm.setString(8, categoryModel.getDesc());
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Category Created Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error","An error Occured" + e.getMessage()).build();
            System.out.println("An error Occured" + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }
    
    @GET
    @Path("category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        int statusCode = 200;
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            Statement etat = com.connexion.Dbconnexion.seconnecter().createStatement();
            ResultSet rs = etat.executeQuery("select * from categories order by categoryid");
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("categoryid",rs.getInt("categoryid"))
                        .add("serviceid", rs.getInt("serviceid"))
                        .add("categoryname", rs.getString("categoryname"))
                        .add("categorydescription", rs.getString("categorydescription"))
                        .add("categorystate", rs.getString("categorystate"))
                        .add("duration", rs.getInt("duration"))
                        .add("price", rs.getDouble("price"))
                        .add("categorycreatedat", rs.getString("categorycreatedat"))
                        .add("categoryupdateat", rs.getString("categoryupdateat"))
                        .build();
                builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
    }
    
    @PUT
    @Path("category/{catId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("catId") int catId,CategoryModel categoryModel) {
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "update categories set categoryname = ?,price = ?,duration = ?,categorystate = ?,categoryupdateat = ?,categorydescription = ? where categoryid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setString(1, categoryModel.getCategoryname());
            pstm.setDouble(2, categoryModel.getPrice());
            pstm.setInt(3, categoryModel.getDuration());
            pstm.setString(4, categoryModel.getCategorystate());
            pstm.setTimestamp(5, timestamp);
            pstm.setString(6, categoryModel.getDesc());
            pstm.setInt(7, catId);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Service Updated Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
             System.out.println("An error Occured" + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    @GET
    @Path("category/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("catId") int catId) {
        JsonObject json = null;
        String sqlStmt = "select * from categories  where categoryid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, catId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("categoryid",rs.getInt("categoryid"))
                        .add("serviceid", rs.getInt("serviceid"))
                        .add("categoryname", rs.getString("categoryname"))
                        .add("categorydescription", rs.getString("categorydescription"))
                        .add("categorystate", rs.getString("categorystate"))
                        .add("duration", rs.getInt("duration"))
                        .add("price", rs.getDouble("price"))
                        .build();
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
    
    @DELETE
    @Path("category/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("catId") int catId) {
        JsonObject json;
        String sqlStmt = "delete from categories where categoryid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, catId);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Category Deleted Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }
}
