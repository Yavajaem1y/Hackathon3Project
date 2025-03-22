package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class DeleteHeroDateUseCase {
    private MainFirebaseRepository firebaseRepository;

    public DeleteHeroDateUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String heroId, UserData userData, BooleanCallBack callBack){
        userData.removeHero(heroId);

        firebaseRepository.deleteHeroData(heroId,userData,callBack);
    }
}
