package com.snapmeal.controllers;

import com.snapmeal.service.ClarifaiService;
import com.snapmeal.service.MicrosoftService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {

    @Autowired
    ClarifaiService imageRecognizerInstance;

    @Autowired
    MicrosoftService microsoftService;

    @GET
    @Path("/microsoft")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recognizeImageMicrosoft() {
        String imageUrl = "http://oi64.tinypic.com/2n9di5d.jpg";

        return Response.ok(microsoftService.recognize(imageUrl)).build();
    }

    @GET
    @Path("/clarifai")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recognizeImage() {
        String imageUrl = "http://oi64.tinypic.com/2n9di5d.jpg";

        // List of Recognized Result from Image
        List<String> resultList = imageRecognizerInstance.recognize(imageUrl);
        return Response.ok(resultList).build();
    }
}
