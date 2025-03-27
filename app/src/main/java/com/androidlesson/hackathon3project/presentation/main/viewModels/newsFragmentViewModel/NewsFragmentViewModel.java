package com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.models.NewsEventPreviewItem;
import com.androidlesson.domain.main.models.NewsHeroPreviewItem;
import com.androidlesson.domain.main.models.NewsItem;
import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;
import com.androidlesson.domain.main.useCase.ProudUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NewsFragmentViewModel extends ViewModel {
    private ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase;
    private ProudUseCase proudUseCase;
    private GetHeroProudListUseCase getHeroProudListUseCase;

    private MutableLiveData<List<NewsItem>> heroesMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> visibilityTopElementMutableLiveData=new MutableLiveData<>(true);
    private MutableLiveData<Integer> filterMutableLiveData=new MutableLiveData<>(1);

    private UserData userData;

    public NewsFragmentViewModel(ObserveHeroesForNewsUseCase observeHeroesForNewsUseCase, ProudUseCase proudUseCase, GetHeroProudListUseCase getHeroProudListUseCase) {
        this.observeHeroesForNewsUseCase = observeHeroesForNewsUseCase;
        this.proudUseCase=proudUseCase;
        this.getHeroProudListUseCase=getHeroProudListUseCase;

        List<NewsItem> items=new ArrayList<>();
        items.add(new NewsEventPreviewItem("Название","id", "1900-1111","Информация","вфывф"));
        items.add(new NewsEventPreviewItem("Название 2","id2", "1300-122","Информация2","вфывф2"));
        items.add(new NewsEventPreviewItem("Название 3","id3", "1500-1133","Информация3","вфывф3"));

        heroesMutableLiveData.setValue(items);

        observe();
    }

    private void observe(){
        observeHeroesForNewsUseCase.execute(new NewsPreviewCallback() {
            @Override
            public void getNewsPreview(NewsHeroPreviewItem hero) {
                List<NewsItem> items=heroesMutableLiveData.getValue();
                assert items != null;
                items.add(hero);
                heroesMutableLiveData.setValue(items);
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
        getHeroProudListUseCase.execute(heroId, new ListStringsCallback() {
            @Override
            public void getList(List<String> list) {
                proudUseCase.execute(new ProudOnHeroModel(heroId,userData.getUserId(),list,userData.getListFavoriteRecordIds()));
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
