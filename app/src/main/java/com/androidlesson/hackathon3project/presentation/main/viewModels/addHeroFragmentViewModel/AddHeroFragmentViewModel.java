package com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.data.main.ImageFirebaseRepository;
import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.AddNewHeroUseCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddHeroFragmentViewModel extends ViewModel {
    private AddNewHeroUseCase addNewHeroUseCase;
    private ImageFirebaseRepository imageFirebaseRepository;

    private UserData currentUser=new UserData();

    private MutableLiveData<Bitmap> heroAvatarImageMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<List<Uri>> imagesMutableLiveData=new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> resultMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> errorCallbackMutableLiveData=new MutableLiveData<>();

    public AddHeroFragmentViewModel(ImageFirebaseRepository imageFirebaseRepository, AddNewHeroUseCase addNewHeroUseCase) {
        this.imageFirebaseRepository = imageFirebaseRepository;
        this.addNewHeroUseCase = addNewHeroUseCase;
    }

    public void addAvatarImage(Bitmap data){
        heroAvatarImageMutableLiveData.setValue(data);
    }

    public void addImage(Uri data) {
        List<Uri> images = imagesMutableLiveData.getValue();

        images.add(data);

        List<Uri> validImages = new ArrayList<>();

        for (Uri uri : images) {
            if (uri != null) {
                validImages.add(uri);
            }
        }

        imagesMutableLiveData.setValue(validImages);
    }

    public void addNewHero(HeroDataToDb data){
        if (currentUser!=null) {
            if (heroAvatarImageMutableLiveData.getValue() != null)
                data.setHeroAvatarImage(convertBitmapToByteArray(heroAvatarImageMutableLiveData.getValue()));
            addNewHeroUseCase.execute(data, currentUser, new HeroDataCallback() {
                @Override
                public void getHeroDataToDb(HeroDataToDb data) {
                    if (!imagesMutableLiveData.getValue().isEmpty()){
                        imageFirebaseRepository.setHeroAdditionalImages(imagesMutableLiveData.getValue(), data.getHeroId(), new BooleanCallBack() {
                            @Override
                            public void getBoolean(Boolean bool) {
                                resultMutableLiveData.setValue(bool);
                            }
                        });
                    }
                    else {
                        resultMutableLiveData.setValue(true);
                    }
                }

                @Override
                public void getHeroData(HeroData data) {

                }
            }, new StringCallBack() {
                @Override
                public void getString(String string) {
                    errorCallbackMutableLiveData.setValue(string);
                }
            });
        }
    }

    public void setCurrentUser(UserData currentUser) {
        this.currentUser = currentUser;
    }

    public LiveData<Bitmap> getHeroAvatarImageMutableLiveData() {
        return heroAvatarImageMutableLiveData;
    }

    public LiveData<List<Uri>> getImagesMutableLiveData() {
        return imagesMutableLiveData;
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return new byte[0];
        }
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


    public LiveData<Boolean> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public LiveData<String> getErrorCallbackMutableLiveData() {
        return errorCallbackMutableLiveData;
    }
}
