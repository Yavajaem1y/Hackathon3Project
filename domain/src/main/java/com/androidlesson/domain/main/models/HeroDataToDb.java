package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class HeroDataToDb {
    private String heroName, heroInfo,heroId,heroDate;
    private byte[] heroAvatarImage;
    private List<String> heroAdditionalImages;
    private List<String> listProud;

    public HeroDataToDb(String heroName, String heroInfo, String heroData) {
        this.heroName = heroName;
        this.heroInfo = heroInfo;
        this.heroDate=heroData;
    }

    public static class InitialHero {
        private String heroName, heroInfo,heroDate;
        private List<String> listProud;

        public InitialHero(String heroName, String heroInfo, List<String> listProud, String heroDate) {
            this.heroName = heroName;
            this.heroInfo = heroInfo;
            this.listProud = listProud != null ? listProud : new ArrayList<>();
            this.heroDate=heroDate;
        }

        public String getHeroName() {
            return heroName;
        }

        public String getHeroInfo() {
            return heroInfo;
        }

        public List<String> getListProud() {
            return listProud;
        }

        public String getHeroDate() {
            return heroDate;
        }
    }

    public List<String> getListProud() {
        return listProud;
    }

    public void setListProud(String id) {
        this.listProud = new ArrayList<>();
        listProud.add(id);
    }

    public String getHeroDate() {
        return heroDate;
    }

    public void setHeroDate(String heroDate) {
        this.heroDate = heroDate;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
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

    public byte[] getHeroAvatarImage() {
        return heroAvatarImage;
    }

    public void setHeroAvatarImage(byte[] heroAvatarImage) {
        this.heroAvatarImage = heroAvatarImage;
    }

    public List<String> getHeroAdditionalImages() {
        return heroAdditionalImages;
    }

    public void setHeroAdditionalImages(List<String> heroAdditionalImages) {
        this.heroAdditionalImages = heroAdditionalImages;
    }

    public void setListProud(List<String> listProud) {
        this.listProud = listProud;
    }
}
