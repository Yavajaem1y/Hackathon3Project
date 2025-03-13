package com.androidlesson.domain.authorization.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentTimeAndDate {

    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.format(new Date());
    }

    public String getCurrentTimeToId(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return formatter.format(new Date());
    }
}
