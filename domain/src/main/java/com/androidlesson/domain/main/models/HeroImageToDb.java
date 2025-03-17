package com.androidlesson.domain.main.models;

public class HeroImageToDb {
    private String imageId,heroId;
    private byte[] imageData;

    public HeroImageToDb(String imageId, String heroId, byte[] imageData) {
        this.imageId = imageId;
        this.heroId = heroId;
        this.imageData = imageData;
    }

    public HeroImageToDb() {
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
