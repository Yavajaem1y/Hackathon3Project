package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;

public class SetAuthorizationDataToSPUseCase {
    private AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public SetAuthorizationDataToSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public void execute(){
        authorizationSharedPreferencesRepository.setAuthorization(true);
    }
}
