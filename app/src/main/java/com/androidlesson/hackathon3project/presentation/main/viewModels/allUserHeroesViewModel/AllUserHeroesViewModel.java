package com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.HeroDataPreviewCallback;
import com.androidlesson.domain.main.models.HeroItemPreview;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserHeroesPreviewUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllUserHeroesViewModel extends ViewModel {
    private ObserveCurrentUserHeroesPreviewUseCase observeCurrentUserHeroesPreviewUseCase;

    private MutableLiveData<UserData> currentUserMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<List<HeroItemPreview>> heroesPreviewMutableLiveData=new MutableLiveData<>(new ArrayList<>());

    public AllUserHeroesViewModel(ObserveCurrentUserHeroesPreviewUseCase observeCurrentUserHeroesPreviewUseCase) {
        this.observeCurrentUserHeroesPreviewUseCase = observeCurrentUserHeroesPreviewUseCase;
    }

    public void observeHero(){
        if(currentUserMutableLiveData.getValue()!=null)
            observeCurrentUserHeroesPreviewUseCase.execute(currentUserMutableLiveData.getValue().getUserId(), new HeroDataPreviewCallback() {
                @Override
                public void getHeroDataPreview(HeroItemPreview hero) {
                    if (hero!=null) {
                        Log.d("HeroAdapter", hero.getId());
                        List<HeroItemPreview> heroes = heroesPreviewMutableLiveData.getValue();
                        if (!heroes.contains(hero)) {
                            heroes.add(hero);
                        }
                        heroesPreviewMutableLiveData.setValue(heroes);
                        Log.d("HeroAdapter", "Size" + heroesPreviewMutableLiveData.getValue().size());
                    }
                }
            });
    }

    public void removeHeroDataById(String heroId){
        List<HeroItemPreview> newList = new ArrayList<>(heroesPreviewMutableLiveData.getValue());
        newList.removeIf(item -> Objects.equals(item.getId(), heroId));
        heroesPreviewMutableLiveData.setValue(newList);
    }

    public void setCurrentUser(UserData data) {
        this.currentUserMutableLiveData.setValue(data);
        observeHero();
    }

    public LiveData<List<HeroItemPreview>> getHeroesPreviewMutableLiveData() {
        return heroesPreviewMutableLiveData;
    }

    public LiveData<UserData> getCurrentUserMutableLiveData() {
        return currentUserMutableLiveData;
    }
}
