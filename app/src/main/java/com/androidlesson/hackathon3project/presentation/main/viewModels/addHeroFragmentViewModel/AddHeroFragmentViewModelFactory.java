package com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.data.main.ImageFirebaseRepository;
import com.androidlesson.domain.main.useCase.AddNewHeroUseCase;

public class AddHeroFragmentViewModelFactory implements ViewModelProvider.Factory {

    private AddNewHeroUseCase addNewHeroUseCase;
    private ImageFirebaseRepository imageFirebaseRepository;

    public AddHeroFragmentViewModelFactory(AddNewHeroUseCase addNewHeroUseCase,ImageFirebaseRepository imageFirebaseRepository) {
        this.addNewHeroUseCase = addNewHeroUseCase;
        this.imageFirebaseRepository=imageFirebaseRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddHeroFragmentViewModel(imageFirebaseRepository,addNewHeroUseCase);
    }
}
