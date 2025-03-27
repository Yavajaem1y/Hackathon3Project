package com.androidlesson.domain.main.models;

public class NewsHeroPreviewItem extends NewsItem {
    private String name, id, date, avatar;

    public NewsHeroPreviewItem(String name, String id, String date, String avatar) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.avatar = avatar;
    }

    @Override
    public String getNewsId() {
        return id;
    }

    @Override
    public int getType() {
        return TYPE_HERO;
    }

    // Геттеры
    public String getName() { return name; }
    public String getId() { return id; }
    public String getDate() { return date; }
    public String getAvatar() { return avatar; }
}
