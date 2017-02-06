package com.snapmeal.controllers;

import com.snapmeal.entity.jpa.User;
import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
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

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        String imgurContent = imageRecognitionService.getImgurContent(fileInputStream);

        String imageUrl = imageRecognitionService.getImageLink(imgurContent);

        String recognizedContent = imageRecognitionService.recognize(imageUrl);

        List<String> tags = imageRecognitionService.getTags(recognizedContent);
        System.out.println(tags);

        String text = imageRecognitionService.getCaptionText(recognizedContent);
        System.out.println(text);

        return Response.ok(recipeService.getRecipeByDescription(text, currentUser)).build();
    }

}
