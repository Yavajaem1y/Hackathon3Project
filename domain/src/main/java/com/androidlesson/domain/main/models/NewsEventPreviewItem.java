package com.androidlesson.domain.main.models;

public class NewsEventPreviewItem extends NewsItem {
    private String name, id, date, info, avatar;

    public NewsEventPreviewItem(String name, String id, String date, String info, String avatar) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.info = info;
        this.avatar = avatar;
    }

    @Override
    public String getNewsId() {
        return id;
    }

    @Override
    public int getType() {
        return TYPE_EVENT;
    }

    // Геттеры
    public String getName() { return name; }
    public String getId() { return id; }
    public String getDate() { return date; }
    public String getInfo() { return info; }
    public String getAvatar() { return avatar; }
}
