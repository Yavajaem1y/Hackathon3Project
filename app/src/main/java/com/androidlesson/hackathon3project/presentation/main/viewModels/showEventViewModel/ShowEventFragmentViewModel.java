package com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.domain.main.useCase.ObserveEventDataUseCase;
import com.androidlesson.domain.main.useCase.ProudEventUseCase;

public class ShowEventFragmentViewModel extends ViewModel {
    private ProudEventUseCase proudEventUseCase;
    private ObserveEventDataUseCase observeEventDataUseCase;

    private MutableLiveData<EventDataFromDB> eventDataMutableLiveData=new MutableLiveData<>();

    public ShowEventFragmentViewModel(ProudEventUseCase proudEventUseCase, ObserveEventDataUseCase observeEventDataUseCase) {
        this.proudEventUseCase = proudEventUseCase;
        this.observeEventDataUseCase = observeEventDataUseCase;
    }

    public void setEventData(String id){
        observeEventDataUseCase.execute(id, new EventDataCallback() {
            @Override
            public void getEventDate(EventDataFromDB eventDataFromDB) {
                eventDataMutableLiveData.setValue(eventDataFromDB);
            }
        });
    }

    public LiveData<EventDataFromDB> getEventDataMutableLiveData() {
        return eventDataMutableLiveData;
    }
}
