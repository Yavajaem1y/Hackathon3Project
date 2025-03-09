package com.androidlesson.domain.authorization.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.utils.AuthorizationUtils;

public class RegistrationUseCase {

    private AuthorizationFirebaseRepository authorizationFirebaseRepository;

    public RegistrationUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository) {
        this.authorizationFirebaseRepository = authorizationFirebaseRepository;
    }

    public void execute(DataToRegistration data, BooleanCallBack booleanCallBack, StringCallBack errorCallBack){
        String email=data.getEmail();
        String password= data.getPassword();
        String repassword = data.getRepassword();
        if (email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            errorCallBack.getString("Поля не должны быть пустыми");
            return;
        }
        else if (password.length()<=5) {
            errorCallBack.getString("Пароль должен быть больше 5 символов");
            return;
        }
        else if (!password.equals(repassword)) {
            errorCallBack.getString("Пароли должны совпадать");
            return;
        }

        else if (!(AuthorizationUtils.isValidEmail(email)))
        {
            errorCallBack.getString("Ваша почта имеет неправильный формат");
            return;
        }
        authorizationFirebaseRepository.registration(data,booleanCallBack,errorCallBack);
    }
}
