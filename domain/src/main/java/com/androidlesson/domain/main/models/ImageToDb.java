package com.androidlesson.domain.main.models;

public class ImageToDb {
    private String imageId,userId;
    private byte[] imageData;

    public ImageToDb(String imageId, String userId, byte[] imageData) {
        this.imageId = imageId;
        this.userId = userId;
        this.imageData = imageData;
    }

    public ImageToDb() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
