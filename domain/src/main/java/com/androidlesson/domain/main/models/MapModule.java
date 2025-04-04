package com.androidlesson.domain.main.models;

import java.util.List;

public class MapModule {
    public String id;
    public String name;
    public List<MapPoint> points;

    public MapModule() {
    }

    public MapModule(String id, String name, List<MapPoint> points) {
        this.id = id;
        this.name = name;
        this.points = points;
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

    public List<MapPoint> getPoints() {
        return points;
    }

    public void setPoints(List<MapPoint> points) {
        this.points = points;
    }
}
