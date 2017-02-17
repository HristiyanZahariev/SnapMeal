package com.snapmeal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapmeal.entity.elasticsearch.DietEs;
import com.snapmeal.entity.jpa.Diet;
import com.snapmeal.repository.elasticsearch.DietEsRepository;
import com.snapmeal.repository.jpa.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by hristiyan on 16.02.17.
 */
@Path("/diet")
public class DietController {

    @Autowired
    DietRepository dietRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    DietEsRepository dietEsRepository;

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    @POST
    //Gotta rework this
    public Response createDiet(DietEs dietEs) throws IOException {
        dietEsRepository.save(dietEs);
        String dietJson = mapper.writeValueAsString(dietEs);
        Diet diet = mapper.readValue(dietJson, Diet.class);
        dietRepository.save(diet);
        return Response.ok(diet).build();
    }


    @DELETE
    @Path("{id}")
    //Gotta rework this
    public Response deleteDiet(@PathParam("id") String id) {
        Long idx = Long.valueOf(id);
        Diet diet = dietRepository.findById(idx);
        dietRepository.delete(diet);
        return Response.ok().build();
    }

}
