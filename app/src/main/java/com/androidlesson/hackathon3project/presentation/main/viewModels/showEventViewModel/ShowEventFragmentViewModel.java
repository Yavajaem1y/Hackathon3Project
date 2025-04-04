package com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.domain.main.models.ProudOnEventModel;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.ObserveEventDataUseCase;
import com.androidlesson.domain.main.useCase.ProudEventUseCase;

public class ShowEventFragmentViewModel extends ViewModel {
    private ProudEventUseCase proudEventUseCase;
    private ObserveEventDataUseCase observeEventDataUseCase;

    private UserData userData;
    private String eventId;

    private MutableLiveData<EventDataFromDB> eventDataMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Boolean> proudMutableLiveData=new MutableLiveData<>(false);
    private MutableLiveData<Boolean> visibilityArticleDayMutableLiveData=new MutableLiveData<>(false);

    public ShowEventFragmentViewModel(ProudEventUseCase proudEventUseCase, ObserveEventDataUseCase observeEventDataUseCase) {
        this.proudEventUseCase = proudEventUseCase;
        this.observeEventDataUseCase = observeEventDataUseCase;
    }

    public void setEventData(String id, String lastId){
        eventId=id;
        visibilityArticleDayMutableLiveData.setValue(eventId.equals(lastId));
        observeEventDataUseCase.execute(id, new EventDataCallback() {
            @Override
            public void getEventDate(EventDataFromDB eventDataFromDB) {
                eventDataMutableLiveData.setValue(eventDataFromDB);
                if (userData!=null){
                    proudMutableLiveData.setValue(userData.getListFavoriteRecordIds().contains(eventId));
                }
            }
        });
    }

    public void setCurrentUser(UserData userData){
        this.userData=userData;
        if (eventId!=null && !eventId.isEmpty()){
            proudMutableLiveData.setValue(userData.getListFavoriteRecordIds().contains(eventId));
        }
    }

    public void proud(){
        if (eventId!=null && !eventId.isEmpty()){
            if (eventDataMutableLiveData.getValue()!=null){
                if (userData!=null){
                    Log.d("ShowEvent","proud");
                    proudEventUseCase.execute(new ProudOnEventModel(eventId,userData.getUserId(),eventDataMutableLiveData.getValue().getEventProudList(),userData.getListFavoriteRecordIds()));
                }
                else Log.d("ShowEvent","userData==null");
            }
            else Log.d("ShowEvent","eventData=null");
        }
        else Log.d("ShowEvent","eventId=null");
    }

    public LiveData<EventDataFromDB> getEventDataMutableLiveData() {
        return eventDataMutableLiveData;
    }

    public LiveData<Boolean> getProudMutableLiveData() {
        return proudMutableLiveData;
    }

    public LiveData<Boolean> getVisibilityArticleDayMutableLiveData() {
        return visibilityArticleDayMutableLiveData;
    }
}
