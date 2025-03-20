package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsFragmentViewModel extends ViewModel {
    private ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase;

    private MutableLiveData<List<NewsPreviewItem>> heroesMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(true);

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

    private String timeLastAction;
    public void setVisibilityTopElement(boolean bool) {
        if (visibilityTopElementMutableLiveData.getValue()!=bool){
            if (timeLastAction == null) {
                timeLastAction = new CurrentTimeAndDate().getCurrentTime();
                visibilityTopElementMutableLiveData.setValue(bool);
            } else {
                try {
                    // Парсим timeLastAction в объект Date
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date lastActionDate = formatter.parse(timeLastAction);
                    Date currentDate = new Date();

                    long differenceInMillis = currentDate.getTime() - lastActionDate.getTime();

                    if (differenceInMillis >= 1000) {
                        visibilityTopElementMutableLiveData.setValue(bool);
                        timeLastAction = new CurrentTimeAndDate().getCurrentTime();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public LiveData<List<NewsPreviewItem>> getHeroesMutableLiveData() {
        return heroesMutableLiveData;
    }

    public LiveData<Boolean> getVisibilityTopElementMutableLiveData() {
        return visibilityTopElementMutableLiveData;
    }
}
