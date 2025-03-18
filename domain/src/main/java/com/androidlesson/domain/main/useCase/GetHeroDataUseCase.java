package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.interfaces.HeroDataPreviewCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class GetHeroDataUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public GetHeroDataUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(String heroId, HeroDataCallback heroDataCallback){
        mainFirebaseRepository.observeHeroData(heroId,heroDataCallback);
    }
}
