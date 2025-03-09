package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;

public class CheckUserAuthorizationUseCase {
    private AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public CheckUserAuthorizationUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public boolean execute(){
        return authorizationSharedPreferencesRepository.getAuthorization();
    }
}
