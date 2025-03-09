package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.main.interfaces.UserDataCallback;

public class CheckUserDataFromFBUseCase {
    private AuthorizationFirebaseRepository firebaseRepository;

    public CheckUserDataFromFBUseCase(AuthorizationFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserDataCallback userDataCallback){
        firebaseRepository.getCurrentUserData(userDataCallback);
    }
}
