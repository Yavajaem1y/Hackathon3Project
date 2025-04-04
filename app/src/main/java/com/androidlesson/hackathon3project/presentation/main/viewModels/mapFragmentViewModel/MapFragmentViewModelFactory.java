package com.androidlesson.hackathon3project.presentation.main.viewModels.mapFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.GetMapModuleUseCase;
import com.androidlesson.domain.main.useCase.SetIsFirstTimeUseCase;

public class MapFragmentViewModelFactory implements ViewModelProvider.Factory {
    private GetMapModuleUseCase getMapModuleUseCase;
    private SetIsFirstTimeUseCase setIsFirstTimeUseCase;

    public MapFragmentViewModelFactory(GetMapModuleUseCase getMapModuleUseCase, SetIsFirstTimeUseCase setIsFirstTimeUseCase) {
        this.getMapModuleUseCase = getMapModuleUseCase;
        this.setIsFirstTimeUseCase = setIsFirstTimeUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MapFragmentViewModel(getMapModuleUseCase,setIsFirstTimeUseCase);
    }
}
