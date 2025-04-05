package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.repository.MapFirebaseRepository;

public class UnlockTheNextPointUseCase {
    private MapFirebaseRepository firebaseRepository;

    public UnlockTheNextPointUseCase(MapFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String userId, int currentPointId, int moduleSize,int pointsCompleted){
        if (currentPointId%10==moduleSize){
            currentPointId=(currentPointId/10+1)*10+1;
        }
        else {
            currentPointId++;
        }
        pointsCompleted++;
        firebaseRepository.UnlockTheNextPointUseCase(userId,currentPointId,pointsCompleted);
    }
}
