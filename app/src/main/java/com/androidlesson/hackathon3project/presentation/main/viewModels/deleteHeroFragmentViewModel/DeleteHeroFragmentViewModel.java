package com.androidlesson.hackathon3project.presentation.main.viewModels.deleteHeroFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.DeleteHeroDateUseCase;

public class DeleteHeroFragmentViewModel extends ViewModel {

    private DeleteHeroDateUseCase deleteHeroDateUseCase;

    private UserData userData;
    private String heroId;

    private MutableLiveData<Boolean> resultMutableLiveData=new MutableLiveData<>();

    public DeleteHeroFragmentViewModel(DeleteHeroDateUseCase deleteHeroDateUseCase) {
        this.deleteHeroDateUseCase = deleteHeroDateUseCase;
    }

    public void setUserData(UserData data){
        userData=data;
    }

    public void deleteHero(){
        if (userData!=null && heroId!=null && !heroId.isEmpty())
        deleteHeroDateUseCase.execute(heroId, userData, new BooleanCallBack() {
            @Override
            public void getBoolean(Boolean bool) {
                resultMutableLiveData.setValue(bool);
            }
        });
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId){
        this.heroId=heroId;
    }

    public LiveData<Boolean> getResultMutableLiveData() {
        return resultMutableLiveData;
    }
}
