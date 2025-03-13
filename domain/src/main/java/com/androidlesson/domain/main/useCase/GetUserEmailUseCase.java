package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;

public class GetUserEmailUseCase {
    private AuthorizationFirebaseRepository firebaseRepository;

    public GetUserEmailUseCase(AuthorizationFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(StringCallBack stringCallBack){
        firebaseRepository.getUserEmail(stringCallBack);
    }
}
