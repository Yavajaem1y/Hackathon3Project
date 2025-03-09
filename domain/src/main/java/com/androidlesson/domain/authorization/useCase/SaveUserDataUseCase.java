package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;

public class SaveUserDataUseCase {

    private AuthorizationFirebaseRepository firebaseRepository;
    private AuthorizationSharedPreferencesRepository sharedPrefRepository;
    private UserDataCallback callbackGetUserData;


    public SaveUserDataUseCase(AuthorizationSharedPreferencesRepository sharedPrefRepository, AuthorizationFirebaseRepository firebaseRepository) {
        this.sharedPrefRepository = sharedPrefRepository;
        this.firebaseRepository = firebaseRepository;
    }

    private UserDataCallback callbackGetUserDataFromDB=new UserDataCallback() {
        @Override
        public void getUserData(UserData userData) {
            sharedPrefRepository.setUserData(userData);
            if (callbackGetUserData!=null)
                callbackGetUserData.getUserData(userData);
        }
    };

    public void execute(UserData user, StringCallBack callbackError, UserDataCallback callbackGetUserData){
        this.callbackGetUserData=callbackGetUserData;
        String id=user.getUserId();
        if (user.getUserName().isEmpty() || user.getUserSurname().isEmpty()){
            callbackError.getString("Поля не могут быть пустыми");
        }
        if(!id.isEmpty()){
            boolean checkLetter = false;

            for (char i : id.toCharArray()) {
                if ((i >= 'A' && i <= 'Z') || (i >= 'a' && i <= 'z')) {
                    checkLetter = true;
                } else if (i >= '0' && i <= '9') {
                    if (!checkLetter) {
                        callbackError.getString("В Id первой должна стоять буква");
                        return;
                    }
                } else {
                    callbackError.getString("Id может содержать в себе только буквы и цифры");
                    return;
                }
            }
            firebaseRepository.checkAvailableIds(id, new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean res) {
                    if (res) callbackError.getString("Данный Id уже занят");
                    else firebaseRepository.saveUserData(user,callbackGetUserDataFromDB);
                }
            });
        }
        else{
            firebaseRepository.getBasicId(new StringCallBack() {
                @Override
                public void getString(String string) {
                    user.setUserId(string);
                    firebaseRepository.saveUserData(user,callbackGetUserDataFromDB);
                }
            });
        }
    }
}
