package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;
import com.androidlesson.domain.main.useCase.ProudUseCase;

public class NewsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase;
    private ProudUseCase proudUseCase;
    private GetHeroProudListUseCase getHeroProudListUseCase;

    public NewsFragmentViewModelFactory(ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase, ProudUseCase proudUseCase, GetHeroProudListUseCase getHeroProudListUseCase) {
        this.observeHeroesForNewsUseCase = observeHeroesForNewsUseCase;
        this.proudUseCase=proudUseCase;
        this.getHeroProudListUseCase=getHeroProudListUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsFragmentViewModel(observeHeroesForNewsUseCase, proudUseCase, getHeroProudListUseCase);
    }


}
