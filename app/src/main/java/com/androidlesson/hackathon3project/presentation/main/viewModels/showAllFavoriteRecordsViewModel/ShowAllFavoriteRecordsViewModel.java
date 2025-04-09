package com.androidlesson.hackathon3project.presentation.main.viewModels.showAllFavoriteRecordsViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.FavoriteRecordCallback;
import com.androidlesson.domain.main.models.FavoriteRecord;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetFavoriteRecordsUseCase;

import java.util.ArrayList;
import java.util.List;

public class ShowAllFavoriteRecordsViewModel extends ViewModel {
    private GetFavoriteRecordsUseCase getFavoriteRecordsUseCase;

    private MutableLiveData<List<FavoriteRecord>> favoriteRecordMutableLiveData=new MutableLiveData<>(new ArrayList<>());

    public ShowAllFavoriteRecordsViewModel(GetFavoriteRecordsUseCase getFavoriteRecordsUseCase) {
        this.getFavoriteRecordsUseCase = getFavoriteRecordsUseCase;
    }

    public void setUserData(UserData data){
        getFavoriteRecordsUseCase.execute(data, new FavoriteRecordCallback() {
            @Override
            public void getFavoriteRecord(FavoriteRecord favoriteRecord) {
                List<FavoriteRecord> newList=favoriteRecordMutableLiveData.getValue();
                if (!newList.contains(favoriteRecord)){
                    newList.add(favoriteRecord);
                    favoriteRecordMutableLiveData.setValue(newList);
                }
            }
        });
    }

    public LiveData<List<FavoriteRecord>> getFavoriteRecordMutableLiveData() {
        return favoriteRecordMutableLiveData;
    }
}
