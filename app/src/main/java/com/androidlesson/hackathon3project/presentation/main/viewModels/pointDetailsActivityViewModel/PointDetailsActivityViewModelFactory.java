package com.androidlesson.hackathon3project.presentation.main.viewModels.pointDetailsActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;

public class PointDetailsActivityViewModelFactory implements ViewModelProvider.Factory {
    private UnlockTheNextPointUseCase unlockTheNextPointUseCase;

    public PointDetailsActivityViewModelFactory(UnlockTheNextPointUseCase unlockTheNextPointUseCase) {
        this.unlockTheNextPointUseCase = unlockTheNextPointUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PointDetailsActivityViewModel(unlockTheNextPointUseCase);
    }
}
