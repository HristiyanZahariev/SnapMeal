package com.snapmeal.service;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hristiyan on 17.12.16.
 */
@Component
public class ClarifaiService {
    // Provide your Client ID
    private final static String CLIENT_ID = "vGz9hODtY7dmjEfUQmp_cXY3XNPLbMPnBcSKovJm";

    // Provider Your Client Secret Key
    private final static String CLIENT_SECRET_KEY = "vVl2qq86GJPix7B5SuSgWNQLL-jsyqhWdOmB4vmo";

    public static List<String> recognize(String imageUrl) {

        // Defining List Object
        List<String> resultList = new ArrayList<String>();

        if(imageUrl != null && !imageUrl.isEmpty()) {

            final ClarifaiClient client = new ClarifaiBuilder(CLIENT_ID, CLIENT_SECRET_KEY).buildSync();

            final List<ClarifaiOutput<Concept>> predictionResults =
                    client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(ClarifaiImage.of(imageUrl))
                            )
                            .executeSync()
                            .get();

            if (predictionResults != null && predictionResults.size() > 0) {

                // Prediction List Iteration
                for (int i = 0; i < predictionResults.size(); i++) {

                    ClarifaiOutput<Concept> clarifaiOutput = predictionResults.get(i);

                    List<Concept> concepts = clarifaiOutput.data();

                    if(concepts != null && concepts.size() > 0) {
                        for (int j = 0; j < concepts.size(); j++) {

                            resultList.add(concepts.get(j).name());
                        }
                    }
                }
            }

        }
        return resultList;
    }


}
