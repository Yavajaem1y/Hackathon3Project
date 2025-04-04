package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.models.ProudOnEventModel;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ProudEventUseCase {
    private MainFirebaseRepository firebaseRepository;

    public ProudEventUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(ProudOnEventModel model){
        if (model.getListProud().contains(model.getUserId())){
            model.unProud();
        }
        else model.proud();
        firebaseRepository.proudEvent(model);
    }
}
