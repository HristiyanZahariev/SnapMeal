package com.snapmeal.entity.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by hristiyan on 24.02.17.
 */
public class RatingEs {

    private Long userId;
    private float value;

    public RatingEs() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEs ratingEs = (RatingEs) o;

        if (Float.compare(ratingEs.value, value) != 0) return false;
        return userId != null ? userId.equals(ratingEs.userId) : ratingEs.userId == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        return result;
    }
}
