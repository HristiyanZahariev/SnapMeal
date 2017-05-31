package com.snapmeal.controllers;

import com.snapmeal.security.JwtUser;
import com.snapmeal.security.UserAuthentication;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {

    @Autowired
    ImageRecognitionService imageRecognitionService;

    @Autowired
    RecipeService recipeService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response searchRecipesWithPicture(
            @FormDataParam("image") InputStream fileInputStream) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser currentJwtUser = ((UserAuthentication) authentication).getDetails();

        String imgurContent = imageRecognitionService.getImgurContent(fileInputStream);

        String imageUrl = imageRecognitionService.getImageLink(imgurContent);

        String recognizedContent = imageRecognitionService.recognize(imageUrl);

        List<String> tags = imageRecognitionService.getTags(recognizedContent);
        System.out.println(tags);

        String text = imageRecognitionService.getCaptionText(recognizedContent);
        System.out.println(text);

        List<String> ids = recipeService.getRecipesByDescription(text, currentJwtUser);


        return Response.ok(recipeService.getRecipesByIds(ids, text)).build();
    }
}
