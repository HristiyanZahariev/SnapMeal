package com.snapmeal.fileUpload.imgur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hristiyan on 11.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurData {

    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
