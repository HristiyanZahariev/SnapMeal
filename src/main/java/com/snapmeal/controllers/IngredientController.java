package com.snapmeal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapmeal.entity.elasticsearch.IngredientEs;
import com.snapmeal.entity.jpa.Diet;
import com.snapmeal.entity.jpa.Ingredient;
import com.snapmeal.repository.elasticsearch.IngredientEsRepository;
import com.snapmeal.repository.jpa.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by hristiyan on 19.02.17.
 */
@Path("/ingredient")
public class IngredientController {


    @Autowired
    IngredientRepository ingredientRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    IngredientEsRepository ingredientEsRepository;

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    @POST
    //Gotta rework this
    public Response createIngredient(IngredientEs ingredientEs) throws IOException {
        ingredientEsRepository.save(ingredientEs);
        String ingredientJson = mapper.writeValueAsString(ingredientEs);
        Ingredient ingredient = mapper.readValue(ingredientJson, Ingredient.class);
        ingredientRepository.save(ingredient);
        return Response.ok(ingredient).build();
    }

}
