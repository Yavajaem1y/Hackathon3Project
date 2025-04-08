package com.androidlesson.domain.main.models;

import java.util.List;

public class HeroData {
    private String heroName, heroInfo, heroAvatarImage,heroDate;
    private List<String> heroAdditionalImages, listProud;
    private String id;

    public HeroData() {
    }

    public HeroData(String heroName, String heroInfo, String heroAvatarImage, List<String> heroAdditionalImages, List<String> listProud, String id) {
        this.heroName = heroName;
        this.heroInfo = heroInfo;
        this.heroAvatarImage = heroAvatarImage;
        this.heroAdditionalImages = heroAdditionalImages;
        this.listProud = listProud;
        this.id = id;
    }

    public HeroData(String heroName, String heroAvatarImage, String id) {
        this.heroName = heroName;
        this.heroAvatarImage = heroAvatarImage;
        this.id = id;
    }

    public String getHeroDate() {
        return heroDate;
    }

    public void setHeroDate(String heroDate) {
        this.heroDate = heroDate;
    }

    public List<String> getListProud() {
        return listProud;
    }

    public void setListProud(List<String> listProud) {
        this.listProud = listProud;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroInfo() {
        return heroInfo;
    }

    public void setHeroInfo(String heroInfo) {
        this.heroInfo = heroInfo;
    }

    public String getHeroAvatarImage() {
        return heroAvatarImage;
    }

    public void setHeroAvatarImage(String heroAvatarImage) {
        this.heroAvatarImage = heroAvatarImage;
    }

    public List<String> getHeroAdditionalImages() {
        return heroAdditionalImages;
    }

    public void setHeroAdditionalImages(List<String> heroAdditionalImages) {
        this.heroAdditionalImages = heroAdditionalImages;
    }
}
