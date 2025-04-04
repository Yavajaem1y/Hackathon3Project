package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.main.models.UserData;

public class SetIsFirstTimeUseCase {
    private AuthorizationFirebaseRepository firebaseRepository;

    public SetIsFirstTimeUseCase(AuthorizationFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(UserData userData){
        firebaseRepository.setIsFirstTime(userData);
    }
}
