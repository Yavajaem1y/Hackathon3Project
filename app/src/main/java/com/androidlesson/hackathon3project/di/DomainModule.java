package com.androidlesson.hackathon3project.di;


import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.authorization.useCase.CheckUserAuthorizationUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromFBUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromSPUseCase;
import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.androidlesson.domain.main.useCase.AddNewHeroUseCase;
import com.androidlesson.domain.main.useCase.DeleteHeroDateUseCase;
import com.androidlesson.domain.main.useCase.EditUserDataUseCase;
import com.androidlesson.domain.main.useCase.GetHeroDataUseCase;
import com.androidlesson.domain.main.useCase.GetHeroProudListUseCase;
import com.androidlesson.domain.main.useCase.GetUserEmailUseCase;
import com.androidlesson.domain.main.useCase.LogOutUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserHeroesPreviewUseCase;
import com.androidlesson.domain.main.useCase.ObserveHeroesForNewsUseCase;
import com.androidlesson.domain.main.useCase.ProudUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    //Shared useCases
    @Provides
    public ObserveCurrentUserDataUseCase provideObserveCurrentUserDataUseCase(MainFirebaseRepository mainFirebaseRepository){
        return new ObserveCurrentUserDataUseCase(mainFirebaseRepository);
    }

    //Authorization UseCases
    @Provides
    public CheckUserDataFromSPUseCase provideCheckUserDataUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new CheckUserDataFromSPUseCase(authorizationSharedPreferencesRepository);
    }

    @Provides
    public CheckUserAuthorizationUseCase provideCheckUserAuthorizationUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new CheckUserAuthorizationUseCase(authorizationSharedPreferencesRepository);
    }

    @Provides
    public SetAuthorizationDataToSPUseCase provideSetAuthorizationDataToSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new SetAuthorizationDataToSPUseCase(authorizationSharedPreferencesRepository);
    }

    @Provides
    public LoginUseCase provideLoginUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository){
        return new LoginUseCase(authorizationFirebaseRepository);
    }

    @Provides
    public RegistrationUseCase provideRegistrationUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository){
        return new RegistrationUseCase(authorizationFirebaseRepository);
    }

    @Provides
    public CheckUserDataFromFBUseCase provideCheckUserDataFromFBUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository){
        return new CheckUserDataFromFBUseCase(authorizationFirebaseRepository);
    }

    @Provides
    public GetUserIdUseCase provideGetUserIdUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new GetUserIdUseCase(authorizationSharedPreferencesRepository);
    }

    @Provides
    public LogOutUseCase provideLogOutUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository, AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new LogOutUseCase(authorizationFirebaseRepository,authorizationSharedPreferencesRepository);
    }

    @Provides
    public SaveUserDataToSPUseCase provideSaveUserDataToSPUseCase(AuthorizationSharedPreferencesRepository authorizationSharedPreferencesRepository){
        return new SaveUserDataToSPUseCase(authorizationSharedPreferencesRepository);
    }

    @Provides
    public EditUserDataUseCase provideEditUserDataUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository){
        return new EditUserDataUseCase(authorizationFirebaseRepository);
    }

    @Provides
    public GetUserEmailUseCase provideGetUserEmailUseCase(AuthorizationFirebaseRepository authorizationFirebaseRepository){
        return new GetUserEmailUseCase(authorizationFirebaseRepository);
    }

    @Provides
    public AddNewHeroUseCase provideAddNewHeroUseCase(MainFirebaseRepository firebaseRepository){
        return new AddNewHeroUseCase(firebaseRepository);
    }

    @Provides
    public ObserveHeroesForNewsUseCase provideObserveHeroesForNewsUseCase(MainFirebaseRepository firebaseRepository){
        return new ObserveHeroesForNewsUseCase(firebaseRepository);
    }

    @Provides
    public ObserveCurrentUserHeroesPreviewUseCase provideObserveCurrentUserHeroesPreviewUseCase(MainFirebaseRepository firebaseRepository){
        return new ObserveCurrentUserHeroesPreviewUseCase(firebaseRepository);
    }

    @Provides
    public GetHeroDataUseCase provideGetHeroDataUseCase(MainFirebaseRepository firebaseRepository){
        return new GetHeroDataUseCase(firebaseRepository);
    }

    @Provides
    public ProudUseCase provideProudUseCase(MainFirebaseRepository firebaseRepository){
        return new ProudUseCase(firebaseRepository);
    }

    @Provides
    public DeleteHeroDateUseCase provideDeleteHeroDateUseCase(MainFirebaseRepository firebaseRepository){
        return new DeleteHeroDateUseCase(firebaseRepository);
    }

    @Provides
    public GetHeroProudListUseCase provideGetHeroProudListUseCase(MainFirebaseRepository firebaseRepository){
        return new GetHeroProudListUseCase(firebaseRepository);
    }
}
