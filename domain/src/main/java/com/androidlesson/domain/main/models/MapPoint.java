package com.androidlesson.domain.main.models;

import java.util.List;

public class MapPoint {
    private int id;
    private String name;
    private List<MapArticleItem> items;
    private String type;

    public MapPoint() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MapArticleItem> getItems() {
        return items;
    }

    public void setItems(List<MapArticleItem> items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
