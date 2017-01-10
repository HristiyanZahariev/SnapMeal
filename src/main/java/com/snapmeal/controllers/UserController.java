package com.snapmeal.controllers;


import com.snapmeal.entity.jpa.User;
import com.snapmeal.security.UserAuthentication;
import com.snapmeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
@Path("/user")
public class UserController {

    @Autowired
    private UserService userInstance;

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrent() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return Response.ok(((UserAuthentication) authentication).getDetails()).build();
        }
        return Response.ok(new User(authentication.getName())).build(); //anonymous user support
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(userInstance.getAllUsers())
                .build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        return Response.ok(userInstance.createUser(user))
                .build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") String id) {
        long idx = Long.valueOf(id).longValue();
        return Response.status(200).entity(userInstance.findUserById(idx)).build();
    }


}
