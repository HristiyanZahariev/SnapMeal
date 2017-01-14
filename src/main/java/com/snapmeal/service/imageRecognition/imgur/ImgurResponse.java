package com.snapmeal.service.imageRecognition.imgur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by hristiyan on 11.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurResponse {
    private ImgurData data;
    private boolean success;
    private int status;

    public ImgurResponse(ImgurData data, boolean success, int status) {
        this.data = data;
        this.success = success;
        this.status = status;
    }

    public ImgurResponse() {
    }

    public ImgurData getData() {
        return data;
    }

    public void setData(ImgurData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
