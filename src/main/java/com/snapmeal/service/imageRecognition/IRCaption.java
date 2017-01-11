package com.snapmeal.service.imageRecognition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hristiyan on 11.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IRCaption {

    private String text;
    private float confidence;

    public IRCaption(String text, float confidence) {
        this.text = text;
        this.confidence = confidence;
    }

    public IRCaption() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
}
