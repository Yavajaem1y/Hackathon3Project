package com.androidlesson.domain.main.models;

import java.io.Serializable;

public class MapArticleItem implements Serializable {
    private String text, image;

    public MapArticleItem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
