package com.snapmeal.controllers;


import com.snapmeal.dao.Recipe;
import com.snapmeal.dao.User;
import com.snapmeal.repositories.RecipeRepository;
import com.snapmeal.repositories.UserRepository;
import com.snapmeal.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
@Path("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(repository.findAll()).build();
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRecipe(User user) {
        User something = repository.save(user);
        return Response.ok(something).build();

    }
}
