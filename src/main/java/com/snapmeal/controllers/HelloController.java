package com.snapmeal.controllers;

import com.snapmeal.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hristiyan on 07.12.16.
 */

@Component
@Path("/")
public class HelloController {

    @Autowired
    HelloService helloService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloMessage() {

        String result = helloService.hello();

        return Response.ok(result).build();

    }

}