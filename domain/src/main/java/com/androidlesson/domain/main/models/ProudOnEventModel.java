package com.androidlesson.domain.main.models;

import java.util.List;

public class ProudOnEventModel {
    private String eventId,userId ;
    private List<String> listProud, listFavoriteRecordIds;

    public ProudOnEventModel(String eventId, String userId, List<String> listProud, List<String> listFavoriteRecordIds) {
        this.eventId = eventId;
        this.userId = userId;
        this.listProud = listProud;
        this.listFavoriteRecordIds = listFavoriteRecordIds;
    }

    public ProudOnEventModel() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public void unProud() {
        if (listProud.contains(userId)) {
            listProud.remove(userId);
        }
        if (listFavoriteRecordIds.contains(eventId)) {
            listFavoriteRecordIds.remove(eventId);
        }
    }

    public void proud() {
        if (!listProud.contains(userId)) {
            listProud.add(userId);
        }
        if (!listFavoriteRecordIds.contains(eventId)) {
            listFavoriteRecordIds.add(eventId);
        }
    }
}
