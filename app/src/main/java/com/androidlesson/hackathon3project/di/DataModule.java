package com.androidlesson.hackathon3project.di;

import android.content.Context;

import com.androidlesson.data.authorization.AuthorizationFirebaseRepositoryImpl;
import com.androidlesson.data.authorization.AuthorizationSharedPreferencesRepositoryImpl;
import com.androidlesson.data.main.ImageFirebaseRepository;
import com.androidlesson.data.main.MainFirebaseRepositoryImpl;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    //Authorization Repository
    @Provides
    public AuthorizationSharedPreferencesRepository providesAuthorizationSharedPreferencesRepository(Context context){
        return new AuthorizationSharedPreferencesRepositoryImpl(context);
    }

    @Provides
    public AuthorizationFirebaseRepository providesAuthorizationFirebaseRepository(){
        return new AuthorizationFirebaseRepositoryImpl();
    }

    //Main Repository
    @Provides
    public MainFirebaseRepository providesMainFirebaseRepository(){
        return new MainFirebaseRepositoryImpl();
    }

    @Provides
    public ImageFirebaseRepository providesImageFirebaseRepository(){
        return new ImageFirebaseRepository();
    }
}
