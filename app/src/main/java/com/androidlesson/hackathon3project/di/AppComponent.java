package com.androidlesson.hackathon3project.di;

import com.androidlesson.hackathon3project.presentation.authorization.activities.AuthorizationActivity;
import com.androidlesson.hackathon3project.presentation.authorization.activities.SetUserDataActivity;
import com.androidlesson.hackathon3project.presentation.authorization.activities.UserSessionCheckerActivity;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.UserProfileFragment;

import dagger.Component;

@Component(modules = {AppModule.class, DomainModule.class, DataModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void injectUserSessionCheckerActivity(UserSessionCheckerActivity userSessionCheckerActivity);

    void injectMainFragment(MainFragment mainFragment);

    void injectAuthorizationActivity(AuthorizationActivity authorizationActivity);

    void injectSetUserDataActivity(SetUserDataActivity setUserDataActivity);

    void injectUserProfileFragment(UserProfileFragment userProfileFragment);
}
