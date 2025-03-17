package com.androidlesson.domain.main.models;

public class StringImageToDb {
    private String imageId,heroId;
    private String imageData;

    public StringImageToDb(String imageId, String heroId, String imageData) {
        this.imageId = imageId;
        this.heroId = heroId;
        this.imageData = imageData;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
