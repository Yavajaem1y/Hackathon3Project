package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.interfaces.HeroDataPreviewCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ObserveCurrentUserHeroesPreviewUseCase {
    private MainFirebaseRepository mainFirebaseRepository;

    public ObserveCurrentUserHeroesPreviewUseCase(MainFirebaseRepository mainFirebaseRepository) {
        this.mainFirebaseRepository = mainFirebaseRepository;
    }

    public void execute(String id, HeroDataPreviewCallback heroDataPreviewCallback){
        mainFirebaseRepository.observeCurrentUserHeroesPreview(id,heroDataPreviewCallback);
    }
}
