package com.androidlesson.hackathon3project.app;

import android.app.Application;

import com.androidlesson.hackathon3project.di.AppComponent;
import com.androidlesson.hackathon3project.di.AppModule;
import com.androidlesson.hackathon3project.di.DaggerAppComponent;

public class App extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
