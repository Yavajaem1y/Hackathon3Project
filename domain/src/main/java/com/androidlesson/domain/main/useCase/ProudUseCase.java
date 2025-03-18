package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import java.util.List;

public class ProudUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public ProudUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(ProudOnHeroModel model){
        if (model.getListProud().contains(model.getUserId())){
            model.unProud();
            f(model);
        }
        else{
            model.proud();
            f(model);
        }
    }

    private void f(ProudOnHeroModel proudOnHeroModel){
        mainFirebaseRepository.proudHero(proudOnHeroModel);
    }
}
