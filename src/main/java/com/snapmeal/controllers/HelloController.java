package com.snapmeal.controllers;

import com.snapmeal.dao.Recipe;
import com.snapmeal.repositories.RecipeRepository;
import com.snapmeal.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hristiyan on 07.12.16.
 */

@Component
@Path("/hello")
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    private RecipeRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloMessage() {
//
        Recipe recipe  = new Recipe("Pesho", "dsadsadsadasas");
        Recipe something = repository.save(recipe);
        String result = helloService.hello();

        return Response.ok(something).build();

    }

}