package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;

public class LogOutUseCase {
    private AuthorizationFirebaseRepository firebaseRepository;
    private AuthorizationSharedPreferencesRepository sharedPrefRepository;

    public LogOutUseCase(AuthorizationFirebaseRepository firebaseRepository, AuthorizationSharedPreferencesRepository sharedPrefRepository) {
        this.firebaseRepository = firebaseRepository;
        this.sharedPrefRepository = sharedPrefRepository;
    }

    public void execute(){
        firebaseRepository.logOut();
        sharedPrefRepository.logOut();
    }
}
