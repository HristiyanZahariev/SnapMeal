package com.snapmeal.service.imageRecognition;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Created by hristiyan on 18.12.16.
 */
@Component
public class ImageRecognitionService {
    @Value("${imageRecognition.subKey}")
    private String subKey;

    public String recognize(String imageUrl) {
        HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/vision/v1.0/analyze");
            System.out.println(subKey);
            builder.setParameter("visualFeatures", "Description");
            builder.setParameter("language", "en");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subKey);
            System.out.println(request);

            // Request body
            StringEntity params = new StringEntity("{\"url\":\"" + imageUrl + "\"} ");
            request.setEntity(params);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

