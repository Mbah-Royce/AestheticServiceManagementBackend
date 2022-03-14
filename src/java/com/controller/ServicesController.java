/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.ServiceModel;
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
@Path("auth/servicescontroller/")
public class ServicesController {

    @GET
    @Path("service")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        int statusCode = 200;
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            Statement etat = com.connexion.Dbconnexion.seconnecter().createStatement();
            ResultSet rs = etat.executeQuery("select * from services order by serviceid");
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("serviceid", rs.getInt("serviceid"))
                        .add("servicename", rs.getString("servicename"))
                        .add("description", rs.getString("descripion"))
                        .add("servicestate", rs.getString("servicestate"))
                        .add("servicecreatedat", rs.getString("servicecreatedat"))
                        .add("serviceupdatedat", rs.getString("serviceupdatedat"))
                        .add("imagepath", rs.getString("imagepath"))
                        .build();
                builder.add(json);
                System.out.println(rs.getString("descripion"));
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(statusCode).entity(builder.build().toString()).build();

    }

    @POST
    @Path("service")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ServiceModel service) {
        JsonObject json;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "insert into services (servicename,descripion,servicestate,servicecreatedat,serviceupdatedat,imagepath) values (?,?,?,?,?,?)";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setString(1, service.getName());
            pstm.setString(2, service.getDescription());
            pstm.setString(3, "active");
            pstm.setTimestamp(4, timestamp);
            pstm.setTimestamp(5, timestamp);
            pstm.setString(6, service.getImagepath());
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Service Created Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }

    @PUT
    @Path("service/{serviceid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("serviceid") int serviceId, ServiceModel service) {
        JsonObject json;
        int statusCode = 200;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "update services set servicename = ?,descripion = ?,servicestate = ?,serviceupdatedat = ? ,imagepath = ? where serviceid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setString(1, service.getName());
            pstm.setString(2, service.getDescription());
            pstm.setString(3, service.getServicestate());
            pstm.setTimestamp(4,timestamp);
            pstm.setString(5, service.getImagepath());
            pstm.setInt(6, serviceId);
            
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Service Updated Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();

    }

    @GET
    @Path("service/{serviceid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("serviceid") int serviceId) {
        JsonObject json = null;
        int statusCode = 200;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        String sqlStmt = "select * from services  where serviceid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, serviceId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("id", rs.getInt("serviceid"))
                        .add("name", rs.getString("servicename"))
                        .add("imagepath", rs.getString("imagepath"))
                        .add("description", rs.getString("descripion"))
                        .add("servicestate", rs.getString("servicestate"))
                        .add("servicecreatedat", rs.getString("servicecreatedat"))
                        .add("serviceupdatedat", rs.getString("serviceupdatedat"))
                        .build();
                builder.add(json);
            }
            sqlStmt = "select * from categories  where serviceid = ?";
            pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, serviceId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("id", rs.getInt("categoryid"))
                        .add("price", rs.getInt("price"))
                        .add("categoryname", rs.getString("categoryname"))
                        .add("categorydescription", rs.getString("categorydescription"))
                        .add("duration", rs.getInt("duration"))
//                        .add("urlpreview", rs.getString("urlpreview"))
//                        .add("title", rs.getString("title"))
                        .build();
                builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(statusCode).entity(builder.build().toString()).build();
    }

    @DELETE
    @Path("service/{serviceid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("serviceid") int serviceId) {
        JsonObject json;
        int statusCode = 200;
        String sqlStmt = "delete from services where serviceid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, serviceId);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Service Created Successfully").build();
            statusCode = 200;
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            statusCode = 500;
        }
        return Response.status(statusCode).entity(json.toString()).build();
    }

    @GET
    @Path("service/appointment/{serviceid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response appointments(@PathParam("serviceid") int serviceId) {
        JsonObject json;
        int statusCode = 200;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        String sqlStmt = "Select * from appointments where sessionid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, serviceId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                json = Json.createObjectBuilder()
                        .add("id", rs.getInt("appointmentid"))
                        .add("date", rs.getString("appointmentdate"))
                        .add("appointment_status", rs.getString("appointmentstatus"))
                        .add("appointment_createat", rs.getString("appointmentcreateat"))
                        .add("appointment_updateat", rs.getString("appointmentupdateat"))
                        .build();
                builder.add(json);
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(statusCode).entity(builder.build().toString()).build();
    }

}
//        String sqlStmt = "Select services.name, services.serviceid, appointments.date, appointments.appointmentid, "
//                + "appointments.userid, appointments.appointmentstatus, appointments.appointmentcreateat, appointments.appointmentupdateat "
//                + "from services left join appointments on services.serviceid = appointments.serviceid";