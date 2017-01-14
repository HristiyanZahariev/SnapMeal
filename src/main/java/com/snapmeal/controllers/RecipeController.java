package com.snapmeal.controllers;

import com.snapmeal.entity.elasticsearch.RecipeEs;
import com.snapmeal.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

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
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecipes() {
       return Response.ok(recipeInstance.getAllRecipes()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") String id) {
        return Response.status(200).entity(recipeInstance.findRecipeById(id)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipe(RecipeEs recipe) {
        return Response.ok(recipeInstance.createRecipe(recipe)).build();

    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeByDescription() {
        return Response.ok(recipeInstance.getRecipeByDescription("This", new PageRequest(0, 10))).build();
    }

}