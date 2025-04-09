package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.domain.main.models.NewsEventPreviewItem;
import com.androidlesson.domain.main.models.NewsHeroPreviewItem;
import com.androidlesson.domain.main.models.NewsItem;
import com.androidlesson.domain.main.models.ProudOnEventModel;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.ObserveEventDataUseCase;
import com.androidlesson.domain.main.useCase.ObserveNewsPreviewUseCase;
import com.androidlesson.domain.main.useCase.ProudEventUseCase;
import com.androidlesson.domain.main.useCase.ProudHeroUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NewsFragmentViewModel extends ViewModel {
    private ObserveNewsPreviewUseCase observeNewsPreviewUseCase;
    private ProudHeroUseCase proudHeroUseCase;
    private GetHeroProudListUseCase getHeroProudListUseCase;
    private ObserveEventDataUseCase observeEventDataUseCase;
    private ProudEventUseCase proudEventUseCase;

    private MutableLiveData<List<NewsItem>> heroesMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(true);
    private MutableLiveData<Integer> filterMutableLiveData=new MutableLiveData<>(1);

    private UserData userData;

    public NewsFragmentViewModel(ObserveNewsPreviewUseCase observeNewsPreviewUseCase, ProudHeroUseCase proudHeroUseCase, GetHeroProudListUseCase getHeroProudListUseCase,ObserveEventDataUseCase observeEventDataUseCase,ProudEventUseCase proudEventUseCase) {
        this.observeNewsPreviewUseCase = observeNewsPreviewUseCase;
        this.proudHeroUseCase = proudHeroUseCase;
        this.getHeroProudListUseCase=getHeroProudListUseCase;
        this.observeEventDataUseCase=observeEventDataUseCase;
        this.proudEventUseCase=proudEventUseCase;

        observe();
    }

    private List<NewsItem> currentList=new ArrayList<>();

    private void observe(){
        observeNewsPreviewUseCase.execute(new NewsPreviewCallback() {
            @Override
            public void getHeroPreview(NewsHeroPreviewItem hero) {
                currentList.add(hero);
                heroesMutableLiveData.postValue(currentList);
            }

            @Override
            public void getEventPreview(NewsEventPreviewItem event) {
                currentList.add(event);
                heroesMutableLiveData.postValue(currentList);
            }
        });
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
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

    public void removeHeroDataById(String heroId) {
        List<NewsItem> newListPreview = new ArrayList<>(heroesMutableLiveData.getValue());

        newListPreview.removeIf(item ->
                item instanceof NewsHeroPreviewItem && Objects.equals(((NewsHeroPreviewItem) item).getId(), heroId)
        );

        heroesMutableLiveData.setValue(newListPreview);
    }

    public void proudOnHero(String heroId){
        if (userData==null) return;
        Log.d("Proud","proud on hero in vm");
        getHeroProudListUseCase.execute(heroId, new ListStringsCallback() {
            @Override
            public void getList(List<String> list) {
                proudHeroUseCase.execute(new ProudOnHeroModel(heroId,userData.getUserId(),list,userData.getListFavoriteRecordIds()));
            }
        });
    }

    public void proudOnEvent(String eventId){
        if (userData==null) {
            Log.d("Proud","userData in vm null");
            return;
        }
        Log.d("Proud","proud on event in vm");
        observeEventDataUseCase.execute(eventId, new EventDataCallback() {
            @Override
            public void getEventDate(EventDataFromDB eventDataFromDB) {
                Log.d("Proud","pround from bd");
                proudEventUseCase.execute(new ProudOnEventModel(eventId,userData.getUserId(),eventDataFromDB.getEventProudList(),userData.getListFavoriteRecordIds()));
            }
        });
    }

    //Type: 1-events, 2-heroes
    public void setFilter(int filterType){
        filterMutableLiveData.setValue(filterType);
    }

    public LiveData<List<NewsItem>> getHeroesMutableLiveData() {
        return heroesMutableLiveData;
    }

    public LiveData<Boolean> getVisibilityTopElementMutableLiveData() {
        return visibilityTopElementMutableLiveData;
    }

    public LiveData<Integer> getFilterMutableLiveData() {
        return filterMutableLiveData;
    }
}
