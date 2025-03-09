package com.androidlesson.domain.main.models;

import java.util.List;

public class UserData {
    private String userName, userSurname, userSystemId, userId;
    private List<String> listAwardsReceivedIds, listHeroIds, listFavoriteRecordIds;

    public UserData() {
    }

    public UserData(String userName, String userSurname, String userSystemId, String userId, List<String> listAwardsReceivedIds, List<String> listHeroIds, List<String> listFavoriteRecordIds) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSystemId = userSystemId;
        this.userId = userId;
        this.listAwardsReceivedIds = listAwardsReceivedIds;
        this.listHeroIds = listHeroIds;
        this.listFavoriteRecordIds = listFavoriteRecordIds;
    }

    public UserData(String userName, String userSurname, String userSystemId, String userId) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSystemId = userSystemId;
        this.userId = userId;
    }

    public UserData(String userName, String userSurname, String userSystemId) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSystemId = userSystemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public void setUserSystemId(String userSystemId) {
        this.userSystemId = userSystemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getListAwardsReceivedIds() {
        return listAwardsReceivedIds;
    }

    public void setListAwardsReceivedIds(List<String> listAwardsReceivedIds) {
        this.listAwardsReceivedIds = listAwardsReceivedIds;
    }

    public List<String> getListHeroIds() {
        return listHeroIds;
    }

    public void setListHeroIds(List<String> listHeroIds) {
        this.listHeroIds = listHeroIds;
    }

    public List<String> getListFavoriteRecordIds() {
        return listFavoriteRecordIds;
    }

    public void setListFavoriteRecordIds(List<String> listFavoriteRecordIds) {
        this.listFavoriteRecordIds = listFavoriteRecordIds;
    }
}
