package com.androidlesson.domain.main.models;

public class NewsPreviewItem {

    private String name, id, date, avatar, newsType;

    public NewsPreviewItem(String name, String id, String date, String avatar, String newsType) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.avatar = avatar;
        this.newsType = newsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }
}
