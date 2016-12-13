package com.snapmeal.controllers;

import com.snapmeal.entity.Recipe;
import com.snapmeal.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hristiyan on 07.12.16.
 */

@Component
@Path("/recipe")
public class RecipeController {

    @Autowired
    private RecipeRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecipes() {
       return Response.ok(repository.findAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") String id) {
        long idx = Long.valueOf(id).longValue();
        return Response.status(200).entity(repository.findOne(idx)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRecipe(Recipe recipe) {
        Recipe something = repository.save(recipe);
        return Response.ok(something).build();

    }

}