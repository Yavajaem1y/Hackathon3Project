package com.androidlesson.domain.main.models;

import java.util.ArrayList;
import java.util.List;

public class EventDataFromDB {
    private String id, eventDate, eventName, eventInfo, eventAvatarImage;
    private List<String> eventProudList;

    public EventDataFromDB(String eventDate, String eventName, String eventInfo, String eventAvatarImage, List<String> eventProudList) {
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.eventInfo = eventInfo;
        this.eventAvatarImage = eventAvatarImage;
        this.eventProudList = eventProudList!=null ? eventProudList: new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventDataFromDB() {
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getEventAvatarImage() {
        return eventAvatarImage;
    }

    public void setEventAvatarImage(String eventAvatarImage) {
        this.eventAvatarImage = eventAvatarImage;
    }

    public List<String> getEventProudList() {
        return eventProudList;
    }

    public void setEventProudList(List<String> eventProudList) {
        this.eventProudList = eventProudList;
    }
}
