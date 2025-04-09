package com.androidlesson.hackathon3project.presentation.main.viewModels.showAllFavoriteRecordsViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetFavoriteRecordsUseCase;

public class ShowAllFavoriteRecordsViewModelFactory implements ViewModelProvider.Factory {
    private GetFavoriteRecordsUseCase getFavoriteRecordsUseCase;

    public ShowAllFavoriteRecordsViewModelFactory(GetFavoriteRecordsUseCase getFavoriteRecordsUseCase) {
        this.getFavoriteRecordsUseCase = getFavoriteRecordsUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowAllFavoriteRecordsViewModel(getFavoriteRecordsUseCase);
    }
}
