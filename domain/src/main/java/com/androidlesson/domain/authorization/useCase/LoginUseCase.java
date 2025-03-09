package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.utils.AuthorizationUtils;

public class LoginUseCase {

    private AuthorizationFirebaseRepository authorizationFirebaseRepository;

    public LoginUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository) {
        this.authorizationFirebaseRepository = authorizationFirebaseRepository;
    }

    public void execute(DataToLogin data, BooleanCallBack booleanCallBack, StringCallBack errorCallBack){
        String email=data.getEmail();
        String password= data.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            errorCallBack.getString("Поля не должны быть пустыми");
            return;
        }
        else if (password.length()<=5) {
            errorCallBack.getString("Пароль должен быть больше 5 символов");
            return;
        }
        else if (!(AuthorizationUtils.isValidEmail(email)))
        {
            errorCallBack.getString("Ваша почта имеет неправильный формат");
            return;
        }
        authorizationFirebaseRepository.login(data,booleanCallBack,errorCallBack);
    }
}
