package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class GetHeroProudListUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public GetHeroProudListUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(String heroId, ListStringsCallback listStringsCallback){
        mainFirebaseRepository.getHeroProudList(heroId,listStringsCallback);
    }
}
