package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroImageToDb;

import java.util.List;

public interface MainFirebaseRepository {
    void observeCurrentUserData(UserDataCallback userDataCallback);

    void addNewHero(HeroDataToDb heroDataToDb, String userId, BooleanCallBack booleanCallBack, StringCallBack errorCallback);

    void setHeroAvatarImage(HeroImageToDb image, BooleanCallBack booleanCallBack, StringCallBack errorCallback);

    void observeNewsPreview(NewsPreviewCallback callback);
}
