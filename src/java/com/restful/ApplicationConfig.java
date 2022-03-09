package com.restful;

import java.util.Set;
import javax.ws.rs.core.Application;


@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application { 

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.controller.AppointmentController.class);
        resources.add(com.controller.CategoryController.class);
        resources.add(com.controller.CategorySessionController.class);
        resources.add(com.controller.LoginController.class);
        resources.add(com.controller.RegistrationController.class);
        resources.add(com.controller.ServicesController.class);
        resources.add(com.controller.UserController.class);
        resources.add(com.controller.VerifyOtpController.class);
        resources.add(com.middleware.BearerTokenFilter.class);
        resources.add(com.restful.GenericResource.class);
    }
    
}