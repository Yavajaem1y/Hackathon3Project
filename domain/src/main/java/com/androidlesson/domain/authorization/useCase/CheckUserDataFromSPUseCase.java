package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.models.UserData;

public class CheckUserDataFromSPUseCase {
    private final AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public CheckUserDataFromSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public UserData execute() {
        return authorizationSharedPreferencesRepository.getUserData();
    }
}
