/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Mbah Royce
 */
public class Dbconnexion {
    private  static Connection conex;
    private String url = "jdbc:postgresql://localhost:5432/java_project";
    private String user = "postgres";
    private String pwd = "21Password3";
    
    private Dbconnexion() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("error of driver :" + e.getMessage());
        }
        try {
            conex = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            System.out.println("error of connectino :"+ e.getMessage());
        }
    }
    public static Connection seconnecter(){
        if(conex == null){
            Dbconnexion connextion = new Dbconnexion();
        }
        return conex;
    }
}
