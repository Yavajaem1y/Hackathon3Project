package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.interfaces.FavoriteRecordCallback;
import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.interfaces.HeroDataPreviewCallback;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroImageToDb;
import com.androidlesson.domain.main.models.ProudOnEventModel;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;

public interface MainFirebaseRepository {
    void observeCurrentUserData(UserDataCallback userDataCallback);

    void addNewHero(HeroDataToDb heroDataToDb, String userId, BooleanCallBack booleanCallBack, StringCallBack errorCallback);

    void setHeroAvatarImage(HeroImageToDb image, BooleanCallBack booleanCallBack, StringCallBack errorCallback);

    void observeNewsPreview(NewsPreviewCallback callback);

    void observeCurrentUserHeroesPreview(String id, HeroDataPreviewCallback heroDataPreviewCallback);

    void observeHeroData(String heroId, HeroDataCallback heroDataCallback);

    void proudHero(ProudOnHeroModel proudOnHeroModel);

    void deleteHeroData(String heroId, UserData userData, BooleanCallBack booleanCallBack);

    void getHeroProudList(String heroId, ListStringsCallback listStringsCallback);

    void observeEventData(String eventId, EventDataCallback eventDataCallback);

    void proudEvent(ProudOnEventModel proudOnEventModel);

    void getHeroDataById(String heroId, FavoriteRecordCallback favoriteRecordCallback);

    void getEventDataById(String eventId, FavoriteRecordCallback favoriteRecordCallback);
}
