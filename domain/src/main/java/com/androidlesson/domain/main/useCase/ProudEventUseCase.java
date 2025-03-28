package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ProudEventUseCase {
    private MainFirebaseRepository firebaseRepository;

    public ProudEventUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(){

    }
}
