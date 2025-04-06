package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;

public interface MapFirebaseRepository {
    void getModulesFromFirebase(MapModulesCallback callback);
    void UnlockTheNextPointUseCase(String userId, int unlockPointId,int pointsCompleted);
    void AddMedalToUser(String userId,int medalId);
}
