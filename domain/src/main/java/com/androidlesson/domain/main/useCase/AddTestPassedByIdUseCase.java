package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.repository.MapFirebaseRepository;

public class AddTestPassedByIdUseCase {
    private MapFirebaseRepository firebaseRepository;

    public AddTestPassedByIdUseCase(MapFirebaseRepository mapFirebaseRepository) {
        this.firebaseRepository = mapFirebaseRepository;
    }

    public void execute(String userId){
        firebaseRepository.AddTestPassed(userId);
    }
}
