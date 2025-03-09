package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ObserveCurrentUserDataUseCase {
    private MainFirebaseRepository firebaseRepository;

    public ObserveCurrentUserDataUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String id,UserDataCallback userDataCallback){
        firebaseRepository.observeCurrentUserData(id,userDataCallback);
    }
}
