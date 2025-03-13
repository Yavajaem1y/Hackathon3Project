package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.utils.AuthorizationUtils;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;

public class RegistrationUseCase {

    private AuthorizationFirebaseRepository firebaseRepository;

    public RegistrationUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository) {
        this.firebaseRepository = authorizationFirebaseRepository;
    }

    public void execute(DataToRegistration data, StringCallBack errorCallBack, UserDataCallback userDataCallback){
        String email=data.getEmail();
        String password= data.getPassword();
        String repassword = data.getRepassword();
        String userName = data.getUserName();
        String userSurname = data.getUserSurname();
        String userId = data.getUserId();
        if (email.isEmpty() || password.isEmpty() || repassword.isEmpty() || userName.isEmpty() || userSurname.isEmpty()) {
            errorCallBack.getString("Обязательные поля не должны быть пустыми");
            return;
        }
        else if (userId.matches("^[0-9].*")){
            errorCallBack.getString("Id не может начинаться с цифры");
            return;
        }
        else if (!(AuthorizationUtils.isValidEmail(email)))
        {
            errorCallBack.getString("Ваша почта имеет неправильный формат");
            return;
        }

        else if (password.length()<8) {
            errorCallBack.getString("");
            return;
        }
        else if (!password.equals(repassword)) {
            errorCallBack.getString("Пароли должны совпадать");
            return;
        }



        if (userId.isEmpty()){
            firebaseRepository.getBasicId(new StringCallBack() {
                @Override
                public void getString(String string) {
                    data.setUserId(string);
                    registration(data,errorCallBack,userDataCallback);
                }
            });
        }
        else {
            firebaseRepository.checkAvailableIds(userId, new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    if (bool) errorCallBack.getString("Пользователь с таким id уже сущетсвует");
                    else registration(data,errorCallBack,userDataCallback);
                }
            });
        }
    }

    private void registration(DataToRegistration data,StringCallBack errorCallBack,UserDataCallback userDataCallback){
        firebaseRepository.registration(data, new BooleanCallBack() {
            @Override
            public void getBoolean(Boolean bool) {
                if (bool) firebaseRepository.saveUserData(new UserData(data.getUserName(), data.getUserSurname(), data.getUserId()), new UserDataCallback() {
                    @Override
                    public void getUserData(UserData userData) {
                        if (userData!=null) userDataCallback.getUserData(userData);
                    }
                });
            }
        }, errorCallBack);
    }
}
