package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.main.repository.MapFirebaseRepository;

public class AddMedalToUserUseCase {
    private MapFirebaseRepository firebaseRepository;

    public AddMedalToUserUseCase(MapFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(String userId,int medalId){
        firebaseRepository.AddMedalToUser(userId,medalId);
    }
}
