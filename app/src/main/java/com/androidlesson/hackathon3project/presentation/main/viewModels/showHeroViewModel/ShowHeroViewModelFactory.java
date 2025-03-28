package com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetHeroDataUseCase;
import com.androidlesson.domain.main.useCase.ProudHeroUseCase;

public class ShowHeroViewModelFactory implements ViewModelProvider.Factory {
    private GetHeroDataUseCase getHeroDataUseCase;
    private ProudHeroUseCase proudHeroUseCase;

    public ShowHeroViewModelFactory(GetHeroDataUseCase getHeroDataUseCase, ProudHeroUseCase proudHeroUseCase) {
        this.getHeroDataUseCase = getHeroDataUseCase;
        this.proudHeroUseCase = proudHeroUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowHeroFragmentViewModel(getHeroDataUseCase, proudHeroUseCase);
    }
}
