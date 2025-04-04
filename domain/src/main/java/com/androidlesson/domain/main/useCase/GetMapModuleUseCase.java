package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.repository.MapFirebaseRepository;

public class GetMapModuleUseCase {
    private MapFirebaseRepository firebaseRepository;

    public GetMapModuleUseCase(MapFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(MapModulesCallback callback){
        firebaseRepository.getModulesFromFirebase(callback);
    }
}
