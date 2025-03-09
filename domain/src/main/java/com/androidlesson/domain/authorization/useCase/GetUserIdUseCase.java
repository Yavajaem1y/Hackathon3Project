package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;

public class GetUserIdUseCase {
    private AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public GetUserIdUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public String execute(){
        return authorizationSharedPreferencesRepository.getUserId();
    }
}
