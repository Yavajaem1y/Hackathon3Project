package com.androidlesson.domain.main.interfaces;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroDataToDb;

public interface HeroDataCallback {
    public void getHeroDataToDb(HeroDataToDb data);
    public void getHeroData(HeroData data);
}
