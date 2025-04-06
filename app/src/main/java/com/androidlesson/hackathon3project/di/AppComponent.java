package com.androidlesson.hackathon3project.di;

import com.androidlesson.hackathon3project.presentation.authorization.activities.MainActivity;
import com.androidlesson.hackathon3project.presentation.authorization.fragments.LoginFragment;
import com.androidlesson.hackathon3project.presentation.authorization.fragments.RegistrationFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.PointDetailsActivity;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.TestActivity;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.MapFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.NewsFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.UserProfileFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AddHeroDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AllUserHeroesDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.DeleteHeroDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.EditUserProfileDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowEventDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowHeroDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.TestResultDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel.EditUserProfileFragmentViewModel;

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

    void injectEditUserProfileFragment(EditUserProfileDialogFragment editUserProfileDialogFragment);

    void injectAddHeroFragment(AddHeroDialogFragment addHeroDialogFragment);

    void injectAllUserHeroesDialogFragment(AllUserHeroesDialogFragment allUserHeroesDialogFragment);

    void injectNewsFragment(NewsFragment newsFragment);

    void injectShowHeroFragment(ShowHeroDialogFragment showHeroDialogFragment);

    void injectDeleteHeroDialogFragment(DeleteHeroDialogFragment deleteHeroDialogFragment);

    void injectShowEventDialogFragment(ShowEventDialogFragment ShowEventDialogFragment);

    void injectMapFragment(MapFragment MapFragment);

    void injectPointDetailsActivity(PointDetailsActivity pointDetailsActivity);

    void injectTestActivity(TestActivity testActivity);

    void injectTestResultDialogFragment(TestResultDialogFragment testResultDialogFragment);
}
