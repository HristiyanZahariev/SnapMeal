package com.snapmeal.controllers;

import com.snapmeal.entity.elasticsearch.RecipeEs;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {

//    String path = "/home/hristiyan/SnapMealDatabase/";

    @Autowired
    ImageRecognitionService imageRecognitionService;

    @Autowired
    RecipeService recipeService;

    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws Exception {

//        String filePath = path + contentDispositionHeader.getFileName();
//
//        imageRecognitionService.saveFile(fileInputStream, filePath);

        String imgurContent = imageRecognitionService.getImgurContent(fileInputStream);

        String imageUrl = imageRecognitionService.getImageLink(imgurContent);

        String recognizedContent = imageRecognitionService.recognize(imageUrl);

        List<String> tags = imageRecognitionService.getTags(recognizedContent);

        return Response.ok(imageRecognitionService.recognize(imageUrl)).build();
    }

}
