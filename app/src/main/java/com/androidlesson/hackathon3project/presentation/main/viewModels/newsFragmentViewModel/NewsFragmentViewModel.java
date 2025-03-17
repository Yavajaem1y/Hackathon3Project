package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;

import java.util.ArrayList;
import java.util.List;

public class NewsFragmentViewModel extends ViewModel {
    private ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase;

    private MutableLiveData<List<NewsPreviewItem>> heroesMutableLiveData=new MutableLiveData<>(new ArrayList<>());

    public NewsFragmentViewModel(ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase) {
        this.observeHeroesForNewsUseCase = observeHeroesForNewsUseCase;

        observe();
    }

    private void observe(){
        observeHeroesForNewsUseCase.execute(new NewsPreviewCallback() {
            @Override
            public void getNewsPreview(NewsPreviewItem hero) {
                List<NewsPreviewItem> items=heroesMutableLiveData.getValue();
                items.add(hero);
                heroesMutableLiveData.setValue(items);
            }
        });
    }

    public LiveData<List<NewsPreviewItem>> getHeroesMutableLiveData() {
        return heroesMutableLiveData;
    }
}
