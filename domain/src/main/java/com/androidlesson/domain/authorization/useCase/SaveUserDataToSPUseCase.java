package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.models.UserData;

public class SaveUserDataToSPUseCase {
    private AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public SaveUserDataToSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public void execute(UserData userData){
        authorizationSharedPreferencesRepository.setUserData(userData);
    }
}
