package com.androidlesson.domain.main.models;

import java.util.List;

public class HeroData {
    private String heroName, heroInfo, heroAvatarImage;
    private List<String> moreImage;

    public HeroData() {
    }

    public HeroData(String heroName, String heroInfo, String heroAvatarImage, List<String> moreImage) {
        this.heroName = heroName;
        this.heroInfo = heroInfo;
        this.heroAvatarImage = heroAvatarImage;
        this.moreImage = moreImage;
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

    public List<String> getMoreImage() {
        return moreImage;
    }

    public void setMoreImage(List<String> moreImage) {
        this.moreImage = moreImage;
    }
}
