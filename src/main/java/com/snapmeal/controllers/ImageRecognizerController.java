package com.snapmeal.controllers;

import com.snapmeal.service.ImageRecognizerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {

    @Autowired
    ImageRecognizerService imageRecognizerInstance;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response recognizeImage() {
        String imageUrl = "http://oi64.tinypic.com/2n9di5d.jpg";

        // List of Recognized Result from Image
        List<String> resultList = imageRecognizerInstance.recognize(imageUrl);
        return Response.ok(resultList).build();
    }
}
