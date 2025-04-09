package com.androidlesson.domain.main.models;
public class Medal {
    private String name,info,info_preview;
    private int medal_image;

    public Medal(String name, String info, int medal_image, String info_preview) {
        this.name = name;
        this.info = info;
        this.medal_image = medal_image;
        this.info_preview=info_preview;
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

    public void setMedal_image(int medal_image) {
        this.medal_image = medal_image;
        this.info_preview="???";
    }

    public String getInfo_preview() {
        return info_preview;
    }
}
