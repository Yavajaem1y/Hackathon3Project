package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;

public class NewsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase;

    public NewsFragmentViewModelFactory(ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase) {
        this.observeHeroesForNewsUseCase = observeHeroesForNewsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsFragmentViewModel(observeHeroesForNewsUseCase);
    }
}
