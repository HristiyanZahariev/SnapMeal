package com.snapmeal.controllers;

import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hristiyan on 17.12.16.
 */
@Path("/image")
public class ImageRecognizerController {

    String path = "/home/hristiyan/SnapMealDatabase/";

    @Autowired
    ImageRecognitionService imageRecognitionService;

    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws Exception {

        String filePath = path + contentDispositionHeader.getFileName();

        imageRecognitionService.saveFile(fileInputStream, filePath);

        String imageUrl = imageRecognitionService.getImgurContent(filePath);

        System.out.println(imageRecognitionService.recognize(imageUrl));
        return Response.ok(imageRecognitionService.recognize(imageUrl)).build();
    }

}
