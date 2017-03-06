package com.snapmeal.service.imageRecognition;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapmeal.fileUpload.imgur.ImgurResponse;
import com.snapmeal.service.imageRecognition.microsoft.IRCaption;
import com.snapmeal.service.imageRecognition.microsoft.IRResponse;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Created by hristiyan on 18.12.16.
 */
@Component
public class ImageRecognitionService {
    @Value("${imageRecognition.subKey}")
    private String subKeyMicrosoftApi;

    @Value("${imgur.subKey}")
    private String imgurKey;

    String url = "https://api.projectoxford.ai/vision/v1.0/analyze";

    ObjectMapper mapper = new ObjectMapper();

    public String recognize(String imageUrl) throws URISyntaxException {

        //setting params
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("visualFeatures", "Description");
        builder.setParameter("language", "en");

        // request body
        RestTemplate rest = new RestTemplate();
        JSONObject request = new JSONObject();
        request.put("url", imageUrl);

        // setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Ocp-Apim-Subscription-Key", subKeyMicrosoftApi);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

        // sending request and parse result
        ResponseEntity<String> resp = rest
                .exchange(builder.build().toString(), HttpMethod.POST, entity, String.class);
        if (resp.getStatusCode() == HttpStatus.OK) {
            return resp.getBody();
        }
        else {
            return null;
        }
    }

    public List<String> getTags(String recognizedContent) throws IOException {
        IRResponse irResponse = mapper.readValue(recognizedContent, IRResponse.class);
        return irResponse.getDescription().getTags();
    }

    public String getCaptionText(String recognizedContent) throws IOException {
        IRResponse irResponse = mapper.readValue(recognizedContent, IRResponse.class);
        List<IRCaption> captions = irResponse.getDescription().getCaptions();

        for (IRCaption caption: captions) {
            return caption.getText();
        }
        return null;
    }

    public void saveFile(InputStream uploadedInputStream, String serverLocation) {
        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
              outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getImgurContent(InputStream inputImage) throws Exception {
        URL url;
        url = new URL("https://api.imgur.com/3/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //create base64 image
        BufferedImage image = null;
        image = ImageIO.read(inputImage);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArray);
        byte[] byteImage = byteArray.toByteArray();
        String dataImage = Base64.encode(byteImage);
        String data = URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(dataImage, "UTF-8");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Client-ID " + imgurKey);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.connect();
        StringBuilder stb = new StringBuilder();
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

//      Get the response
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        return stb.toString();
    }

    public String getImageLink(String imgurContent) throws IOException {
        ImgurResponse imgurResponse = mapper.readValue(imgurContent, ImgurResponse.class);
        return imgurResponse.getData().getLink().toString();
    }

}

