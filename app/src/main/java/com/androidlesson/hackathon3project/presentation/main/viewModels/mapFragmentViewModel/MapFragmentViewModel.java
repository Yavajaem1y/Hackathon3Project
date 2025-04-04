package com.androidlesson.hackathon3project.presentation.main.viewModels.mapFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetMapModuleUseCase;
import com.androidlesson.domain.main.useCase.SetIsFirstTimeUseCase;

import java.util.ArrayList;
import java.util.List;

public class MapFragmentViewModel extends ViewModel {
    private GetMapModuleUseCase getMapModuleUseCase;
    private SetIsFirstTimeUseCase setIsFirstTimeUseCase;

    private MutableLiveData<List<MapModule>> mapModuleMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<UserData> currentUserDataMutableLiveData=new MutableLiveData<>();

    public MapFragmentViewModel(GetMapModuleUseCase getMapModuleUseCase,SetIsFirstTimeUseCase setIsFirstTimeUseCase) {
        this.getMapModuleUseCase = getMapModuleUseCase;
        this.setIsFirstTimeUseCase=setIsFirstTimeUseCase;

        getMapModuleUseCase.execute(new MapModulesCallback() {
            @Override
            public void getMapModule(MapModule mapModule) {
                if (mapModule!=null){
                    List<MapModule> moduleList = mapModuleMutableLiveData.getValue();
                    moduleList.add(mapModule);
                    mapModuleMutableLiveData.setValue(moduleList);
                }
            }
        });
    }

    public void setIsFirstTime(){
        if (currentUserDataMutableLiveData.getValue()!=null) {
            setIsFirstTimeUseCase.execute(currentUserDataMutableLiveData.getValue());
        }
    }

    public void setCurrentUserData(UserData userData){
        currentUserDataMutableLiveData.setValue(userData);
    }

    public LiveData<List<MapModule>> getMapModuleMutableLiveData() {
        return mapModuleMutableLiveData;
    }

    public MutableLiveData<UserData> getCurrentUserDataMutableLiveData() {
        return currentUserDataMutableLiveData;
    }
}
