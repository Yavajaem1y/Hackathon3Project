package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class ObserveEventDataUseCase {
    private MainFirebaseRepository firebaseRepository;

    public ObserveEventDataUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String eventId, EventDataCallback eventDataCallback){
        firebaseRepository.observeEventData(eventId,eventDataCallback);
    }
}
