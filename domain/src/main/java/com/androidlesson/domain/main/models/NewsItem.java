package com.androidlesson.domain.main.models;

public abstract class NewsItem {
    public static final int TYPE_HERO = 0;
    public static final int TYPE_EVENT = 1;

    public abstract int getType();

    public abstract String getNewsId();
}




