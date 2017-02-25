package com.snapmeal.controllers;

import com.snapmeal.entity.elasticsearch.RecipeEs;
import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.security.JwtUser;
import com.snapmeal.security.UserAuthentication;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by hristiyan on 07.12.16.
 */

@Path("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeInstance;


    //SHOULD REWORK !!!!
    @Autowired
    UserService userService;
    
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
    @Path("/rating")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rateRecipe(@QueryParam("recipe_id") Long recipeId,
                               @QueryParam("rating") int rating) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = ((UserAuthentication) authentication).getDetails();
        recipeInstance.rateRecipe(recipeId, rating, jwtUser);
        return Response.ok().build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipe(RecipeEs recipe) throws IOException {
        return Response.ok(recipeInstance.createRecipe(recipe)).build();

    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeByDescription() {
        return Response.ok().build();
    }

}