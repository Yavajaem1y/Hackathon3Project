package com.androidlesson.hackathon3project.presentation.main.viewModels.deleteHeroFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.DeleteHeroDateUseCase;

public class DeleteHeroFragmentViewModelFactory implements ViewModelProvider.Factory {
    private DeleteHeroDateUseCase deleteHeroDateUseCase;

    public DeleteHeroFragmentViewModelFactory(DeleteHeroDateUseCase deleteHeroDateUseCase) {
        this.deleteHeroDateUseCase = deleteHeroDateUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DeleteHeroFragmentViewModel(deleteHeroDateUseCase);
    }
}
