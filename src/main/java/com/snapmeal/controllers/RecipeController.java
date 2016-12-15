package com.snapmeal.controllers;

import com.snapmeal.entity.Recipe;
import com.snapmeal.repository.RecipeRepository;
import com.snapmeal.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hristiyan on 07.12.16.
 */

@Path("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeInstance;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecipes() {
       return Response.ok(recipeInstance.getAllRecipes()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") String id) {
        long idx = Long.valueOf(id).longValue();
        return Response.status(200).entity(recipeInstance.findRecipeById(idx)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipe(Recipe recipe) {
        return Response.ok(recipeInstance.createRecipe(recipe)).build();

    }

}