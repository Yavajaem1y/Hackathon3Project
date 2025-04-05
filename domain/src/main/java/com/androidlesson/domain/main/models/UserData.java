package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private String userName, userSurname, userSystemId, userId, imageData;
    private List<String> listAwardsReceivedIds= new ArrayList<>(), listHeroIds= new ArrayList<>(), listFavoriteRecordIds= new ArrayList<>();
    private int currentPoint,isFirstTime,testsCompleted,pointsCompleted;

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

    public UserData(String userName, String userSurname, String userId) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userId = userId;
    }

    public int getTestsCompleted() {
        return testsCompleted;
    }

    public void setTestsCompleted(int testsCompleted) {
        this.testsCompleted = testsCompleted;
    }

    public int getPointsCompleted() {
        return pointsCompleted;
    }

    public void setPointsCompleted(int pointsCompleted) {
        this.pointsCompleted = pointsCompleted;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getIsFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(int isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
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

    public void removeHero(String id){
        this.getListHeroIds().remove(id);
    }
}
