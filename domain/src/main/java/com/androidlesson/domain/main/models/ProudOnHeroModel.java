package com.androidlesson.domain.main.models;

import java.util.List;

public class ProudOnHeroModel {
    private String heroId,userId ;
    private List<String> listProud, listFavoriteRecordIds;

    public ProudOnHeroModel(String heroId, String userId, List<String> listProud, List<String> listFavoriteRecordIds) {
        this.heroId = heroId;
        this.userId = userId;
        this.listProud = listProud;
        this.listFavoriteRecordIds = listFavoriteRecordIds;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getListProud() {
        return listProud;
    }

    public void setListProud(List<String> listProud) {
        this.listProud = listProud;
    }

    public List<String> getListFavoriteRecordIds() {
        return listFavoriteRecordIds;
    }

    public void setListFavoriteRecordIds(List<String> listFavoriteRecordIds) {
        this.listFavoriteRecordIds = listFavoriteRecordIds;
    }

    public void unProud(){
        listProud.remove(userId);
        listFavoriteRecordIds.remove(heroId);
    }

    public void proud(){
        listProud.add(userId);
        listFavoriteRecordIds.add(heroId);
    }
}
