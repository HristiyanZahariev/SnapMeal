package com.snapmeal.controllers;

import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {


    @Autowired
    ImageRecognitionService imageRecognitionService;

    @GET
    @Path("/microsoft")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recognizeImageMicrosoft() {
        String imageUrl = "http://oi64.tinypic.com/2n9di5d.jpg";

        return Response.ok(imageRecognitionService.recognize(imageUrl)).build();
    }
}
