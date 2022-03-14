/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mbah Royce
 */
@Path("visitorcontroller/")
public class VisitorController {
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
}
