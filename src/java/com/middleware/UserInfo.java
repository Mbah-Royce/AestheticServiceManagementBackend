/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.middleware;

import com.jwttoken.JavaJWT;
import io.jsonwebtoken.Claims;

/**
 *
 * @author Mbah Royce
 */
public class UserInfo {
    public static String getUserId(String authHeader){
        String[] parts = authHeader.split("\\s");
        Claims claim = JavaJWT.decodeJWT(parts[1]);
        return claim.getId();
    }
}
