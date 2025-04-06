package com.androidlesson.domain.main.models;
public class Medal {
    private String name,info;
    private int medal_image;

    public Medal(String name, String info, int medal_image) {
        this.name = name;
        this.info = info;
        this.medal_image = medal_image;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getMedal_image() {
        return medal_image;
    }
}
