package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.ObserveNewsPreviewUseCase;
import com.androidlesson.domain.main.useCase.ProudHeroUseCase;

public class NewsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private ObserveNewsPreviewUseCase observeNewsPreviewUseCase;
    private ProudHeroUseCase proudHeroUseCase;
    private GetHeroProudListUseCase getHeroProudListUseCase;

    public NewsFragmentViewModelFactory(ObserveNewsPreviewUseCase observeNewsPreviewUseCase, ProudHeroUseCase proudHeroUseCase, GetHeroProudListUseCase getHeroProudListUseCase) {
        this.observeNewsPreviewUseCase = observeNewsPreviewUseCase;
        this.proudHeroUseCase = proudHeroUseCase;
        this.getHeroProudListUseCase=getHeroProudListUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsFragmentViewModel(observeNewsPreviewUseCase, proudHeroUseCase, getHeroProudListUseCase);
    }


}
