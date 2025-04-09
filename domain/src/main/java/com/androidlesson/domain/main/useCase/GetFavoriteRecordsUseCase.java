package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.interfaces.FavoriteRecordCallback;
import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.domain.main.models.FavoriteRecord;
import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.ArrayList;
import java.util.List;

public class GetFavoriteRecordsUseCase {
    private MainFirebaseRepository firebaseRepository;

    public GetFavoriteRecordsUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData userData, FavoriteRecordCallback favoriteRecordCallback){
        List<String> recordsIds=userData.getListFavoriteRecordIds();
        List<String> alreadyExists=new ArrayList<>();
        for (String recordId:recordsIds){
            if (!alreadyExists.contains(recordId)) {
                if (recordId.length() == 14) {
                    alreadyExists.add(recordId);
                    firebaseRepository.getEventDataById(recordId, favoriteRecordCallback);
                }
                else {
                    alreadyExists.add(recordId);
                    firebaseRepository.getHeroDataById(recordId, favoriteRecordCallback);
                }
            }
        }
    }
}
