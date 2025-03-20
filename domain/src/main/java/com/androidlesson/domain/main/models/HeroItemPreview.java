package com.androidlesson.domain.main.models;

public class HeroItemPreview {
    private String id,name,avatar,info, date;

    public HeroItemPreview(String id, String name, String avatar, String info, String date) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.info = info;
        this.date=date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
