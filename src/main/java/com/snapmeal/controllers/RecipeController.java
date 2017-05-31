package com.snapmeal.controllers;

import com.snapmeal.entity.RecipeJsonApi;
import com.snapmeal.entity.elasticsearch.RecipeEs;
import com.snapmeal.entity.jpa.Ingredient;
import com.snapmeal.entity.jpa.Recipe;
import com.snapmeal.entity.jpa.RecipeIngredient;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.repository.jpa.RecipeRepository;
import com.snapmeal.security.JwtUser;
import com.snapmeal.security.UserAuthentication;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.Tags;
import com.snapmeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hristiyan on 07.12.16.
 */

@Path("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeInstance;

    @Autowired
    RecipeRepository recipeRepository;

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
                               @QueryParam("rating") float rating) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = ((UserAuthentication) authentication).getDetails();
        recipeInstance.rateRecipe(recipeId, rating, jwtUser);
        return Response.ok().build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRecipe(RecipeJsonApi recipe) throws IOException {
        return Response.ok(recipeInstance.createRecipe(recipe)).build();

    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomRecipe() {
        List<String> ids = recipeInstance.getRandomRecipes();
        return Response.ok(recipeInstance.getRecipesByIds(ids, "")).build();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRecipeByDescription(List<Tags> tags) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = ((UserAuthentication) authentication).getDetails();
        List<String> ids = recipeInstance.getRecipeByTags(tags, jwtUser);
        String description = new String();
        for (Tags tag : tags) {
            description += tag.getName();
        }
        return Response.ok(recipeInstance.getRecipesByIds(ids, description)).build();
    }

    @GET
    @Path("/recommend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendations() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = ((UserAuthentication) authentication).getDetails();
        User currentUser = userService.getNonJwtUser(jwtUser);
        List<Long> ids = recipeInstance.getRecommendations(currentUser.getId());
        return Response.ok(recipeInstance.getRecipesFromIdsUser(ids, currentUser)).build();
    }

}