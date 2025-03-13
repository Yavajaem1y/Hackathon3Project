package com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.androidlesson.domain.main.useCase.EditUserDataUseCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditUserProfileFragmentViewModel extends ViewModel {
    private EditUserDataUseCase editUserDataUseCase;
    private SaveUserDataToSPUseCase saveUserDataToSPUseCase;

    private MutableLiveData<String> errorCallbackMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> userNameMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> userSurnameMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> userIdMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> userNameAndSurnameMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Bitmap> userAvatarMutableLiveData=new MutableLiveData<>();

    public EditUserProfileFragmentViewModel(EditUserDataUseCase editUserDataUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase) {
        this.editUserDataUseCase = editUserDataUseCase;
        this.saveUserDataToSPUseCase = saveUserDataToSPUseCase;
    }

    public void editData(UserDataToEdit data){
        if(userAvatarMutableLiveData.getValue()!=null){
            data.setUserAvatarImage(convertBitmapToByteArray(userAvatarMutableLiveData.getValue()));
        }
        editUserDataUseCase.execute(data, new StringCallBack() {
            @Override
            public void getString(String string) {
                errorCallbackMutableLiveData.setValue(string);
            }
        }, new UserDataCallback() {
            @Override
            public void getUserData(UserData userData) {
                if (userData!=null) {
                    saveUserDataToSPUseCase.execute(userData);
                    userDataMutableLiveData.setValue(userData);
                }
            }
        });
    }

    public LiveData<UserData> getUserDataMutableLiveData() {
        return userDataMutableLiveData;
    }

    public LiveData<String> getErrorCallbackMutableLiveData() {
        return errorCallbackMutableLiveData;
    }

    public void setUserAvatarMutableLiveData(Bitmap data){
        userAvatarMutableLiveData.setValue(data);
    }

    public void setUserNameMutableLiveData(String name) {
        userNameMutableLiveData.setValue(name);
        userNameAndSurnameMutableLiveData.setValue(name + " " + userSurnameMutableLiveData.getValue());
    }

    public void setUserSurnameMutableLiveData(String surname) {
        userSurnameMutableLiveData.setValue(surname);
        userNameAndSurnameMutableLiveData.setValue(userNameMutableLiveData.getValue() + " " + surname);
    }

    public LiveData<String> getUserNameAndSurnameMutableLiveData() {
        return userNameAndSurnameMutableLiveData;
    }

    public MutableLiveData<String> getUserIdMutableLiveData() {
        return userIdMutableLiveData;
    }

    public void setUserIdMutableLiveData(String id) {
        userIdMutableLiveData.setValue(id);
    }

    public LiveData<Bitmap> getUserAvatarMutableLiveData() {
        return userAvatarMutableLiveData;
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}
