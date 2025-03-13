package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.utils.AuthorizationUtils;
import com.androidlesson.domain.authorization.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.UserDataToEdit;

public class EditUserDataUseCase {
    private AuthorizationFirebaseRepository authorizationFirebaseRepository;

    public EditUserDataUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository) {
        this.authorizationFirebaseRepository = authorizationFirebaseRepository;
    }

    public void execute(UserDataToEdit userDataToEdit, StringCallBack errorCallback, UserDataCallback userDataCallback){
        if (userDataToEdit.getUserEmail().isEmpty() || userDataToEdit.getUserId().isEmpty() ||
                userDataToEdit.getUserName().isEmpty() || userDataToEdit.getUserSurname().isEmpty()){
            errorCallback.getString("Все поля должны быть заполенны");
            return;
        }
        else if (!AuthorizationUtils.isValidEmail(userDataToEdit.getUserEmail())){
            errorCallback.getString("Ваша почта имеет направильный формат");
            return;
        }
        else if(!(userDataToEdit.getUserId().equals(userDataToEdit.getCurrentId()))){
            authorizationFirebaseRepository.checkAvailableIds(userDataToEdit.getUserId(), new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    if (bool) {
                        errorCallback.getString("Пользователь с таким id уже существует");
                    }
                    else {
                        authorizationFirebaseRepository.editId(userDataToEdit.getCurrentId(), userDataToEdit.getUserId(), new BooleanCallBack() {
                            @Override
                            public void getBoolean(Boolean bool) {
                                editEmail(userDataToEdit,errorCallback,userDataCallback);
                            }
                        });
                    }
                }
            });
        }
        else {
            editEmail(userDataToEdit,errorCallback,userDataCallback);
        }
    }

    private void editEmail(UserDataToEdit userDataToEdit, StringCallBack errorCallback, UserDataCallback userDataCallback){
        if (!userDataToEdit.getUserEmail().equals(userDataToEdit.getCurrentEmail()))
            authorizationFirebaseRepository.editEmail(userDataToEdit.getUserEmail(), new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    if (!bool) errorCallback.getString("Что-то пошло не так");
                    else{
                        addUserAvatarImage(userDataToEdit,userDataCallback);
                    }
                }
            });
        else {
            addUserAvatarImage(userDataToEdit,userDataCallback);
        }
    }

    private void addUserAvatarImage(UserDataToEdit userDataToEdit, UserDataCallback userDataCallback){
        if (userDataToEdit.getUserAvatarImage()==null){
            editUserData(userDataToEdit,userDataCallback);
        }
        else {
            String imageId="images/avatar/"+userDataToEdit.getUserSystemId()+ new CurrentTimeAndDate().getCurrentTimeToId()+".jpg";
            authorizationFirebaseRepository.addImageAvatar(new ImageToDb(imageId, userDataToEdit.getUserId(), userDataToEdit.getUserAvatarImage()), new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    editUserData(userDataToEdit,userDataCallback);
                }
            });
        }

    }

    private void editUserData(UserDataToEdit userDataToEdit, UserDataCallback userDataCallback){
        authorizationFirebaseRepository.editUserData(userDataToEdit,userDataCallback);
    }

}
