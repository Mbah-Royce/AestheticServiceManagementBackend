/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.middleware;

import com.jwttoken.JavaJWT;
import static com.jwttoken.JavaJWT.decodeJWT;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Mbah Royce
 */
@Provider
@PreMatching
public class BearerTokenFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo info = requestContext.getUriInfo();
        if (!info.getPath().contains("auth")) {
        return;
        }
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println("header is" + authHeader);
        if (authHeader == null) throw new NotAuthorizedException("Bearer");
        String[] parts = authHeader.split("\\s");
        try {
             if( parts.length == 2 && parts[0].equals("Bearer")){
                 Claims claim = JavaJWT.decodeJWT(parts[1]);
             }else{
                 throw new NotAuthorizedException("Bearer");
             }
        } catch (Exception e) {
            throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
        }
        
    }
    
}
