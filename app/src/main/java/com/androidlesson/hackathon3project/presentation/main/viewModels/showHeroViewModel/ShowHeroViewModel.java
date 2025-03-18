package com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetHeroDataUseCase;
import com.androidlesson.domain.main.useCase.ProudUseCase;

public class ShowHeroViewModel extends ViewModel {
    private GetHeroDataUseCase getHeroDataUseCase;
    private ProudUseCase proudUseCase;

    private String heroId;

    private MutableLiveData<UserData> currentUserMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<HeroData> heroDataMutableLiveData=new MutableLiveData<>();

    public ShowHeroViewModel(GetHeroDataUseCase getHeroDataUseCase, ProudUseCase proudUseCase) {
        this.getHeroDataUseCase = getHeroDataUseCase;
        this.proudUseCase = proudUseCase;
    }

    public void setCurrentUser(UserData userData){
        currentUserMutableLiveData.setValue(userData);
    }

    public void setHeroId(String heroId){
        this.heroId=heroId;
        getHeroDataUseCase.execute(heroId, new HeroDataCallback() {
            @Override
            public void getHeroDataToDb(HeroDataToDb data) {

            }

            @Override
            public void getHeroData(HeroData data) {
                heroDataMutableLiveData.setValue(data);
            }
        });
    }

    public void proud(){
        if (heroDataMutableLiveData.getValue()!=null && currentUserMutableLiveData.getValue()!=null){
            proudUseCase.execute(new ProudOnHeroModel(heroId,currentUserMutableLiveData.getValue().getUserId(),heroDataMutableLiveData.getValue().getListProud(),currentUserMutableLiveData.getValue().getListFavoriteRecordIds()));
        }
    }

    public void dismiss(){
        heroDataMutableLiveData.setValue(null);
        currentUserMutableLiveData.setValue(null);
        heroId = null;
    }

    public LiveData<HeroData> getHeroDataMutableLiveData() {
        return heroDataMutableLiveData;
    }
}
