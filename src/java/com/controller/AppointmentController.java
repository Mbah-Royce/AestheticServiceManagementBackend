/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.middleware.UserInfo;
import com.model.AppointmentModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
@Path("auth/appointmentcontroller/")
public class AppointmentController {

    @GET
    @Path("appointment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            String sqlStmt = "select * from appointments order by appointmentid";
            PreparedStatement pstmt = con.prepareStatement(sqlStmt);
            ResultSet res = pstmt.executeQuery();
            if (res.isBeforeFirst()) {
                while (res.next()) {
                    json = Json.createObjectBuilder()
                            .add("appointmentid", res.getInt("appointmentid"))
                            .add("sessionid", res.getInt("sessionid"))
                            .add("userid", res.getInt("userid"))
                            .add("appointmentdate", res.getString("appointmentdate"))
                            .add("appointmentstatus", res.getString("appointmentstatus"))
                            .add("appointmentcreateat", res.getString("appointmentcreateat"))
                            .add("appointmentupdateat", res.getString("appointmentupdateat"))
                            .add("ispaid", res.getBoolean("ispaid"))
                            .build();
                    builder.add(json);
                }
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
    }

    @POST
    @Path("appointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("AUTHORIZATION") String token, AppointmentModel appointment) {
        String text = appointment.getAppointmentdate();
        LocalDateTime dateTime = LocalDate.parse(text).atStartOfDay();
        Timestamp appointmentDate = Timestamp.valueOf(dateTime);
        JsonObject json = null;
        int userId = Integer.parseInt(UserInfo.getUserId(token));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "insert into appointments (userid,sessionid,appointmentdate,appointmentstatus,appointmentcreateat,appointmentupdateat,ispaid) values (?,?,?,?,?,?,?)";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, userId);
            pstm.setInt(2, appointment.getSessionid());
            pstm.setTimestamp(3, appointmentDate);
            pstm.setString(4, appointment.getAppointmentstatus());
            pstm.setTimestamp(5, timestamp);
            pstm.setTimestamp(6, timestamp);
            pstm.setBoolean(7, true);
            System.out.println(appointment.getAppointmentdate());
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Appointment Created Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", "An error Occured" + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }

    @GET
    @Path("appointment/{apptnid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("apptnid") int apptnid, AppointmentModel appointment) {
        JsonObject json = null;
        String sqlStmt = "select * from appointments where appointmentid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, apptnid);
            ResultSet res = pstm.executeQuery();
            if (res.isBeforeFirst()) {
                while (res.next()) {
                    json = Json.createObjectBuilder()
                            .add("appointmentid", res.getInt("appointmentid"))
                            .add("sessionid", res.getInt("sessionid"))
                            .add("userid", res.getInt("userid"))
                            .add("appointmentdate", res.getString("appointmentdate"))
                            .add("appointmentstatus", res.getString("appointmentstatus"))
                            .add("appointmentcreateat", res.getString("appointmentcreateat"))
                            .add("appointmentupdateat", res.getString("appointmentupdateat"))
                            .add("ispaid", res.getBoolean("ispaid"))
                            .build();
                }
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", "An error occured " + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @GET
    @Path("user/appointment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam("AUTHORIZATION") String token, AppointmentModel appointment) {
        int apptnid = Integer.parseInt(UserInfo.getUserId(token));
        System.out.println(apptnid);
        JsonObject json = null;
        String sqlStmt = "select * from appointments where userid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, 100);
            ResultSet res = pstm.executeQuery();
            System.out.println(res.isBeforeFirst());
            if (res.isBeforeFirst()) {
                while (res.next()) {
                    json = Json.createObjectBuilder()
                            .add("appointmentid", res.getInt("appointmentid"))
                            .add("sessionid", res.getInt("sessionid"))
                            .add("userid", res.getInt("userid"))
                            .add("appointmentdate", res.getString("appointmentdate"))
                            .add("appointmentstatus", res.getString("appointmentstatus"))
                            .add("appointmentcreateat", res.getString("appointmentcreateat"))
                            .add("appointmentupdateat", res.getString("appointmentupdateat"))
                            .add("ispaid", res.getBoolean("ispaid"))
                            .build();
                }
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", "An error occured " + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @PUT
    @Path("appointment/{apptnid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("apptnid") int apptnid, AppointmentModel appointment) {
        String text = appointment.getAppointmentdate();
        LocalDateTime dateTime = LocalDate.parse(text).atStartOfDay();
        Timestamp appointmentDate = Timestamp.valueOf(dateTime);
        JsonObject json = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sqlStmt = "update appointments set sessionid = ?,appointmentdate = ?,appointmentstatus = ?,appointmentupdateat = ?,ispaid = ?) values (?,?,?,?,?)";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, appointment.getSessionid());
            pstm.setTimestamp(2, appointmentDate);
            pstm.setString(3, appointment.getAppointmentstatus());
            pstm.setTimestamp(4, timestamp);
            pstm.setBoolean(5, appointment.isIspaid());
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Appointment Updated Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", "An error Occured" + e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.CREATED).entity(json.toString()).build();
    }

    @DELETE
    @Path("appointment/{apptnid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("apptnid") int apptnid) {
        JsonObject json;
        String sqlStmt = "delete from appointments where appointmentid = ?";
        try {
            Connection con = com.connexion.Dbconnexion.seconnecter();
            PreparedStatement pstm = con.prepareStatement(sqlStmt);
            pstm.setInt(1, apptnid);
            pstm.executeUpdate();
            json = Json.createObjectBuilder().add("message", "Appoinment Deleted Successfully").build();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();

    }
}
