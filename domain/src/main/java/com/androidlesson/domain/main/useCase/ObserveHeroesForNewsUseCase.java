package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ObserveHeroesForNewsUseCase {
    private MainFirebaseRepository firebaseRepository;

    public ObserveHeroesForNewsUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(NewsPreviewCallback callback){
        firebaseRepository.observeNewsPreview(callback);
    }
}
