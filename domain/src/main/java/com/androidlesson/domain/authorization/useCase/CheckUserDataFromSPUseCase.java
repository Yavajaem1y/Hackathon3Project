package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.models.UserData;

public class CheckUserDataFromSPUseCase {
    private final AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository;

    public CheckUserDataFromSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository) {
        this.authorizationSharedPreferencesRepository = authorizationSharedPreferencesRepository;
    }

    public UserData execute() {
        UserData userData = authorizationSharedPreferencesRepository.getUserData();
        if (userData == null) return null;
        else if (userData.getUserId()!=null && !userData.getUserId().isEmpty() && userData.getUserSystemId()!=null && !userData.getUserSystemId().isEmpty()
                && userData.getUserName()!=null &&  !userData.getUserName().isEmpty() && userData.getUserSurname()!=null && !userData.getUserSurname().isEmpty()) return null;
        else return userData;
    }
}
