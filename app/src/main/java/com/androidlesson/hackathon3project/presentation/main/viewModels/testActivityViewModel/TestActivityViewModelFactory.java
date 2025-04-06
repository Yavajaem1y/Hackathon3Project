package com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.AddMedalToUserUseCase;
import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;

public class TestActivityViewModelFactory implements ViewModelProvider.Factory {
    private AddMedalToUserUseCase addMedalToUserUseCase;
    private UnlockTheNextPointUseCase unlockTheNextPointUseCase;

    public TestActivityViewModelFactory(AddMedalToUserUseCase addMedalToUserUseCase, UnlockTheNextPointUseCase unlockTheNextPointUseCase) {
        this.addMedalToUserUseCase = addMedalToUserUseCase;
        this.unlockTheNextPointUseCase = unlockTheNextPointUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TestActivityViewModel(addMedalToUserUseCase,unlockTheNextPointUseCase);
    }
}
