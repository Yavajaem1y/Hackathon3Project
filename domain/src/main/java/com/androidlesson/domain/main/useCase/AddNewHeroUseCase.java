package com.androidlesson.domain.main.useCase;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.utils.CheckDate;
import com.androidlesson.domain.authorization.utils.CurrentTimeAndDate;
import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroImageToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

public class AddNewHeroUseCase {

    private MainFirebaseRepository firebaseRepository;

    public AddNewHeroUseCase(MainFirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void execute(HeroDataToDb data, UserData userData, HeroDataCallback heroDataCallback, StringCallBack errorCallback){
        if (data.getHeroName().isEmpty()){
            errorCallback.getString("Заполните поле \"ФИО\"");
        }
        else if (data.getHeroDate().isEmpty()){
            errorCallback.getString("Заполните поле \"Годы жизни\"");
        }
        else {
            if (!CheckDate.isValid(data.getHeroDate())){
                errorCallback.getString("Вы неправильно заполнили поле \"Годы жизни\"");
                return;
            }
            String[] parts = data.getHeroDate().split("-");
            if (!CheckDate.isValidFirstNumber(parts[0])) {
                errorCallback.getString("Нашим алгоритмам кажется, что ваш герой никак не связан с ВОВ");
                return;
            }
            if (data.getHeroInfo().isEmpty()) {
                data.setHeroInfo("Информация отсутсвует");
            }
            data.setHeroId(new CurrentTimeAndDate().getCurrentTimeToId() + userData.getUserSystemId());
            data.setListProud(userData.getUserId());
            firebaseRepository.addNewHero(data, userData.getUserId(), new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    addHeroAvatarImage(data, userData, heroDataCallback, errorCallback);
                }}, errorCallback);
        }
    }

    public void addHeroAvatarImage(HeroDataToDb data, UserData userData, HeroDataCallback booleanCallBack, StringCallBack errorCallback){
        if (data.getHeroAvatarImage()!=null){
            String imageId="images/heroesAvatar/"+userData.getUserSystemId()+ new CurrentTimeAndDate().getCurrentTimeToId()+".jpg";
            firebaseRepository.setHeroAvatarImage(new HeroImageToDb(imageId, data.getHeroId(), data.getHeroAvatarImage()), new BooleanCallBack() {
                @Override
                public void getBoolean(Boolean bool) {
                    if (bool) booleanCallBack.getHeroDataToDb(data);
                    else booleanCallBack.getHeroDataToDb(null);
                }
            }, errorCallback);
        }
        else booleanCallBack.getHeroDataToDb(data);
    }

}