package com.snapmeal.service.imageRecognition.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by hristiyan on 11.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IRDescription {

    private List<String> tags;
    private List<IRCaption> captions;

    public IRDescription(List<String> tags, List<IRCaption> captions) {
        this.tags = tags;
        this.captions = captions;
    }

    public List<IRCaption> getCaptions() {
        return captions;
    }

    public void setCaptions(List<IRCaption> captions) {
        this.captions = captions;
    }

    public IRDescription() {
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
