package com.androidlesson.domain.main.interfaces;

import com.androidlesson.domain.main.models.EventDataFromDB;

public interface EventDataCallback {
    public void getEventDate(EventDataFromDB eventDataFromDB);
}
