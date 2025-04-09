package com.androidlesson.hackathon3project.di;

import android.app.Application;
import android.content.Context;

import com.androidlesson.data.main.ImageFirebaseRepository;
import com.androidlesson.domain.authorization.useCase.CheckUserAuthorizationUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromFBUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromSPUseCase;
import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;
import com.androidlesson.domain.main.useCase.AddMedalToUserUseCase;
import com.androidlesson.domain.main.useCase.AddNewHeroUseCase;
import com.androidlesson.domain.main.useCase.AddTestPassedByIdUseCase;
import com.androidlesson.domain.main.useCase.DeleteHeroDateUseCase;
import com.androidlesson.domain.main.useCase.EditUserDataUseCase;
import com.androidlesson.domain.main.useCase.GetFavoriteRecordsUseCase;
import com.androidlesson.domain.main.useCase.GetHeroDataUseCase;
import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.GetMapModuleUseCase;
import com.androidlesson.domain.main.useCase.GetUserEmailUseCase;
import com.androidlesson.domain.main.useCase.LogOutUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserHeroesPreviewUseCase;
import com.androidlesson.domain.main.useCase.ObserveEventDataUseCase;
import com.androidlesson.domain.main.useCase.ObserveNewsPreviewUseCase;
import com.androidlesson.domain.main.useCase.ProudEventUseCase;
import com.androidlesson.domain.main.useCase.ProudHeroUseCase;
import com.androidlesson.domain.main.useCase.SetIsFirstTimeUseCase;
import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.loginViewModel.LoginFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.registratoinViewModel.RegistrationFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel.AllUserHeroesViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.deleteHeroFragmentViewModel.DeleteHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel.EditUserProfileFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mapFragmentViewModel.MapFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.pointDetailsActivityViewModel.PointDetailsActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showAllFavoriteRecordsViewModel.ShowAllFavoriteRecordsViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel.ShowEventFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel.ShowHeroViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    public SharedViewModelFactory provideSharedViewModelFactory(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, GetUserIdUseCase getUserIdUseCase, GetUserEmailUseCase getUserEmailUseCase) {
        return new SharedViewModelFactory(application, observeCurrentUserDataUseCase, getUserIdUseCase, getUserEmailUseCase);
    }

    @Provides
    public RegistrationFragmentViewModelFactory provideRegistrationFragmentViewModelFactory(RegistrationUseCase registrationUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        return new RegistrationFragmentViewModelFactory(registrationUseCase,setAuthorizationDataToSPUseCase);
    }

    @Provides
    public MainActivityViewModelFactory provideUserSessionCheckerActivityViewModelFactory(LogOutUseCase logOutUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase, CheckUserDataFromFBUseCase checkUserDataFromFBUseCase, CheckUserDataFromSPUseCase checkUserDataFromSPUseCase, CheckUserAuthorizationUseCase checkUserAuthorizationUseCase){
        return new MainActivityViewModelFactory(logOutUseCase, saveUserDataToSPUseCase, checkUserDataFromFBUseCase, checkUserDataFromSPUseCase, checkUserAuthorizationUseCase);
    }

    @Provides
    public LoginFragmentViewModelFactory provideLoginFragmentViewModelFactory(LoginUseCase loginUseCase,SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        return new LoginFragmentViewModelFactory(loginUseCase,setAuthorizationDataToSPUseCase);
    }

    @Provides
    public MainFragmentViewModelFactory provideMainActivityViewModelFactory() {
        return new MainFragmentViewModelFactory();
    }

    @Provides
    public EditUserProfileFragmentViewModelFactory provideEditUserProfileFragmentViewModelFactory(EditUserDataUseCase editUserDataUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase) {
        return new EditUserProfileFragmentViewModelFactory(editUserDataUseCase,saveUserDataToSPUseCase);
    }

    @Provides
    public AddHeroFragmentViewModelFactory provideAddHeroFragmentViewModelFactory(AddNewHeroUseCase addNewHeroUseCase, ImageFirebaseRepository imageFirebaseRepository) {
        return new AddHeroFragmentViewModelFactory(addNewHeroUseCase,imageFirebaseRepository);
    }

    @Provides
    public NewsFragmentViewModelFactory provideNewsFragmentViewModelFactory(ObserveNewsPreviewUseCase observeNewsPreviewUseCase, ProudHeroUseCase proudHeroUseCase, GetHeroProudListUseCase getHeroProudListUseCase,ObserveEventDataUseCase observeEventDataUseCase,ProudEventUseCase proudEventUseCase) {
        return new NewsFragmentViewModelFactory(observeNewsPreviewUseCase, proudHeroUseCase, getHeroProudListUseCase,observeEventDataUseCase,proudEventUseCase);
    }

    @Provides
    public AllUserHeroesViewModelFactory provideAllUserHeroesViewModelFactory(ObserveCurrentUserHeroesPreviewUseCase observeCurrentUserHeroesPreviewUseCase) {
        return new AllUserHeroesViewModelFactory(observeCurrentUserHeroesPreviewUseCase);
    }

    @Provides
    public ShowHeroViewModelFactory provideShowHeroViewModelFactory(GetHeroDataUseCase getHeroDataUseCase, ProudHeroUseCase proudHeroUseCase) {
        return new ShowHeroViewModelFactory(getHeroDataUseCase, proudHeroUseCase);
    }

    @Provides
    public DeleteHeroFragmentViewModelFactory provideDeleteHeroFragmentViewModelFactory(DeleteHeroDateUseCase deleteHeroDateUseCase) {
        return new DeleteHeroFragmentViewModelFactory(deleteHeroDateUseCase);
    }

    @Provides
    public ShowEventFragmentViewModelFactory provideShowEventFragmentViewModelFactory(ProudEventUseCase proudEventUseCase, ObserveEventDataUseCase observeEventDataUseCase) {
        return new ShowEventFragmentViewModelFactory(proudEventUseCase,observeEventDataUseCase);
    }

    @Provides
    public MapFragmentViewModelFactory provideMapFragmentViewModelFactory(GetMapModuleUseCase getMapModuleUseCase, SetIsFirstTimeUseCase setIsFirstTimeUseCase) {
        return new MapFragmentViewModelFactory(getMapModuleUseCase,setIsFirstTimeUseCase);
    }

    @Provides
    public PointDetailsActivityViewModelFactory providePointDetailsActivityViewModelFactory(UnlockTheNextPointUseCase unlockTheNextPointUseCase) {
        return new PointDetailsActivityViewModelFactory(unlockTheNextPointUseCase);
    }

    @Provides
    public TestActivityViewModelFactory provideTestActivityViewModelFactory(AddMedalToUserUseCase addMedalToUserUseCase, UnlockTheNextPointUseCase unlockTheNextPointUseCase, AddTestPassedByIdUseCase addTestPassedByIdUseCase) {
        return new TestActivityViewModelFactory(addMedalToUserUseCase,unlockTheNextPointUseCase,addTestPassedByIdUseCase);
    }

    @Provides
    public ShowAllFavoriteRecordsViewModelFactory provideShowAllFavoriteRecordsViewModelFactory(GetFavoriteRecordsUseCase getFavoriteRecordsUseCase) {
        return new ShowAllFavoriteRecordsViewModelFactory(getFavoriteRecordsUseCase);
    }
}

