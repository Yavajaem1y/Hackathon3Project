package com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetHeroDataUseCase;
import com.androidlesson.domain.main.useCase.ProudUseCase;

public class ShowHeroViewModelFactory implements ViewModelProvider.Factory {
    private GetHeroDataUseCase getHeroDataUseCase;
    private ProudUseCase proudUseCase;

    public ShowHeroViewModelFactory(GetHeroDataUseCase getHeroDataUseCase, ProudUseCase proudUseCase) {
        this.getHeroDataUseCase = getHeroDataUseCase;
        this.proudUseCase = proudUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowHeroViewModel(getHeroDataUseCase,proudUseCase);
    }
}
