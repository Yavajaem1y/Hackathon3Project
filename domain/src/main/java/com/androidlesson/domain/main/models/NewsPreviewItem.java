package com.androidlesson.domain.main.models;

public class NewsPreviewItem {
    private String name, id, info, avatar, newsType;

    public NewsPreviewItem(String name, String id, String info, String avatar, String newsType) {
        this.name = name;
        this.id = id;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
