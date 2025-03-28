package com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.ObserveEventDataUseCase;
import com.androidlesson.domain.main.useCase.ProudEventUseCase;

public class ShowEventFragmentViewModelFactory implements ViewModelProvider.Factory {
    private ProudEventUseCase proudEventUseCase;
    private ObserveEventDataUseCase observeEventDataUseCase;

    public ShowEventFragmentViewModelFactory(ProudEventUseCase proudEventUseCase, ObserveEventDataUseCase observeEventDataUseCase) {
        this.proudEventUseCase = proudEventUseCase;
        this.observeEventDataUseCase = observeEventDataUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowEventFragmentViewModel(proudEventUseCase,observeEventDataUseCase);
    }
}
