package com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.AddMedalToUserUseCase;
import com.androidlesson.domain.main.useCase.AddTestPassedByIdUseCase;
import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;

public class TestActivityViewModelFactory implements ViewModelProvider.Factory {
    private AddMedalToUserUseCase addMedalToUserUseCase;
    private UnlockTheNextPointUseCase unlockTheNextPointUseCase;
    private AddTestPassedByIdUseCase addTestPassedByIdUseCase;

    public TestActivityViewModelFactory(AddMedalToUserUseCase addMedalToUserUseCase, UnlockTheNextPointUseCase unlockTheNextPointUseCase, AddTestPassedByIdUseCase addTestPassedByIdUseCase) {
        this.addMedalToUserUseCase = addMedalToUserUseCase;
        this.unlockTheNextPointUseCase = unlockTheNextPointUseCase;
        this.addTestPassedByIdUseCase = addTestPassedByIdUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TestActivityViewModel(addMedalToUserUseCase,unlockTheNextPointUseCase,addTestPassedByIdUseCase);
    }
}
