package com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.ObserveCurrentUserHeroesPreviewUseCase;

public class AllUserHeroesViewModelFactory implements ViewModelProvider.Factory {
    private ObserveCurrentUserHeroesPreviewUseCase observeCurrentUserHeroesPreviewUseCase;

    public AllUserHeroesViewModelFactory(ObserveCurrentUserHeroesPreviewUseCase observeCurrentUserHeroesPreviewUseCase) {
        this.observeCurrentUserHeroesPreviewUseCase = observeCurrentUserHeroesPreviewUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AllUserHeroesViewModel(observeCurrentUserHeroesPreviewUseCase);
    }
}
