package com.snapmeal.service.imageRecognition.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hristiyan on 11.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IRResponse {

    private IRDescription description;

    public IRResponse(IRDescription description) {
        this.description = description;
    }

    public IRResponse() {
    }

    public IRDescription getDescription() {
        return description;
    }

    public void setDescription(IRDescription description) {
        this.description = description;
    }
}
