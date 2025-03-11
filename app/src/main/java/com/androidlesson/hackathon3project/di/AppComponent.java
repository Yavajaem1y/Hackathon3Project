package com.androidlesson.hackathon3project.di;

import com.androidlesson.hackathon3project.presentation.authorization.activities.MainActivity;
import com.androidlesson.hackathon3project.presentation.authorization.fragments.LoginFragment;
import com.androidlesson.hackathon3project.presentation.authorization.fragments.RegistrationFragment;
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

    void injectUserSessionCheckerActivity(MainActivity mainActivity);

    void injectMainFragment(MainFragment mainFragment);

    void injectLoginFragment(LoginFragment loginFragment);

    void injectRegistrationFragment(RegistrationFragment registrationFragment);

    void injectUserProfileFragment(UserProfileFragment userProfileFragment);
}
