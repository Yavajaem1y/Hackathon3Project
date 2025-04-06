package com.androidlesson.hackathon3project.presentation.main.viewModels.pointDetailsActivityViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;

import java.util.List;

public class PointDetailsActivityViewModel extends ViewModel {
    private UnlockTheNextPointUseCase unlockTheNextPointUseCase;

    public String pointName,userId;
    private int pointId, moduleSize,userLastPoint,pointsCompleted;
    public List<MapArticleItem> pointItems;

    public PointDetailsActivityViewModel(UnlockTheNextPointUseCase unlockTheNextPointUseCase) {
        this.unlockTheNextPointUseCase = unlockTheNextPointUseCase;
    }

    public void setValues(String pointName, List<MapArticleItem> pointItems, int pointId, int moduleSize, String userId,int userLastPoint, int pointsCompleted){
        this.pointName=pointName;
        this.moduleSize=moduleSize;
        this.pointId=pointId;
        this.pointItems=pointItems;
        this.userId=userId;
        this.userLastPoint=userLastPoint;
        this.pointsCompleted=pointsCompleted;
    }

    public void unlockNextPoint(){
        if (pointId!=0 && moduleSize!=0 && userId!=null){
            if (userLastPoint==pointId) {
                unlockTheNextPointUseCase.execute(userId, pointId, moduleSize, pointsCompleted);
            }
        }
    }
}
