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
import com.androidlesson.domain.main.useCase.ProudHeroUseCase;

public class ShowHeroFragmentViewModel extends ViewModel {
    private GetHeroDataUseCase getHeroDataUseCase;
    private ProudHeroUseCase proudHeroUseCase;

    private String heroId;

    private MutableLiveData<UserData> currentUserMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<HeroData> heroDataMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Boolean> visibilityDotsMenuMutableLiveData =new MutableLiveData<>(false);
    private MutableLiveData<Boolean> selectedHeartMutableLiveData=new MutableLiveData<>(false);

    public ShowHeroFragmentViewModel(GetHeroDataUseCase getHeroDataUseCase, ProudHeroUseCase proudHeroUseCase) {
        this.getHeroDataUseCase = getHeroDataUseCase;
        this.proudHeroUseCase = proudHeroUseCase;
    }

    public void setCurrentUser(UserData userData){
        if (heroId!=null && !heroId.isEmpty()){
            if (userData.getListHeroIds().contains(heroId)){
                visibilityDotsMenuMutableLiveData.setValue(true);
            }
            selectedHeartMutableLiveData.setValue(userData.getListFavoriteRecordIds().contains(heroId));


        }
        currentUserMutableLiveData.setValue(userData);
    }

    public void setHeroId(String heroId){
        this.heroId=heroId;
        if (currentUserMutableLiveData.getValue()!=null){
            if (currentUserMutableLiveData.getValue().getListHeroIds().contains(heroId)){
                visibilityDotsMenuMutableLiveData.setValue(true);
            }
            selectedHeartMutableLiveData.setValue(currentUserMutableLiveData.getValue().getListFavoriteRecordIds().contains(heroId));
        }
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
            proudHeroUseCase.execute(new ProudOnHeroModel(heroId,currentUserMutableLiveData.getValue().getUserId(),heroDataMutableLiveData.getValue().getListProud(),currentUserMutableLiveData.getValue().getListFavoriteRecordIds()));
        }
    }

    public LiveData<HeroData> getHeroDataMutableLiveData() {
        return heroDataMutableLiveData;
    }

    public LiveData<Boolean> getVisibilityDotsMenuMutableLiveData() {
        return visibilityDotsMenuMutableLiveData;
    }

    public LiveData<Boolean> getSelectedHeartMutableLiveData() {
        return selectedHeartMutableLiveData;
    }
}
