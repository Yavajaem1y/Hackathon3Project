package com.androidlesson.data.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroImageToDb;
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    private final String DATABASE_WITH_USERS_DATA ="USERS_DATA_DATABASE";
    private final String DATABASE_SYSTEM_ID_TO_APP_ID="DATABASE_SYSTEM_ID_TO_APP_ID";
    private final String DATABASE_WITH_HEROES_DATA="DATABASE_WITH_HEROES_DATA";

    private final String USER_SYSTEM_ID="userSystemId";
    private final String USER_ID="userId";
    private final String USER_LIST_HEROES_IDS="listHeroIds";
    private final String USER_LIST_FAVORITE_RECORDS_IDS="listFavoriteRecordIds";
    private final String HERO_NAME="heroName";
    private final String HERO_INFO="heroInfo";
    private final String HERO_AVATAR_IMAGE="heroAvatarImage";
    private final String HERO_ADDITIONAL_IMAGES="heroAdditionalImages";
    private final String HERO_LIST_PROUD="listProud";


    private String userSystemId;

    public MainFirebaseRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userSystemId=auth.getUid();
    }

    @Override
    public void observeCurrentUserData(UserDataCallback userDataCallback) {
        if (auth.getUid()!=null) {
            firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userId = snapshot.child(USER_ID).getValue(String.class);
                        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    UserData userData = snapshot.getValue(UserData.class);
                                    userData.setUserId(snapshot.getKey());
                                    userDataCallback.getUserData(userData);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void addNewHero(HeroDataToDb heroDataToDb, String userId, BooleanCallBack booleanCallBack, StringCallBack errorCallback) {
        Log.d("data",heroDataToDb.getHeroId());
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA)
                .child(heroDataToDb.getHeroId())
                .setValue(new HeroDataToDb.InitialHero(heroDataToDb.getHeroName(),heroDataToDb.getHeroInfo(),heroDataToDb.getListProud()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                                    .child(userId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {  // Заменил addValueEventListener на addListenerForSingleValueEvent
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                // Используем GenericTypeIndicator
                                                GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};

                                                List<String> user_heroIds = snapshot.child(USER_LIST_HEROES_IDS).getValue(typeIndicator);
                                                if (user_heroIds == null) user_heroIds = new ArrayList<>();
                                                user_heroIds.add(heroDataToDb.getHeroId());

                                                firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                                                        .child(userId)
                                                        .child(USER_LIST_HEROES_IDS)
                                                        .setValue(user_heroIds);

                                                List<String> user_favoriteRecordsIds = snapshot.child(USER_LIST_FAVORITE_RECORDS_IDS).getValue(typeIndicator);
                                                if (user_favoriteRecordsIds == null) user_favoriteRecordsIds = new ArrayList<>();
                                                user_favoriteRecordsIds.add(heroDataToDb.getHeroId());

                                                firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                                                        .child(userId)
                                                        .child(USER_LIST_FAVORITE_RECORDS_IDS)
                                                        .setValue(user_favoriteRecordsIds);

                                                booleanCallBack.getBoolean(true);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            errorCallback.getString("Ошибка чтения данных из Firebase: " + error.getMessage());
                                        }
                                    });
                        } else {
                            errorCallback.getString("Что-то пошло не так");
                        }
                    }
                });
    }


    @Override
    public void setHeroAvatarImage(HeroImageToDb image, BooleanCallBack booleanCallBack, StringCallBack errorCallback) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(image.getImageId());
        storageRef.putBytes(image.getImageData())
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveHeroImageAvatarUrl(uri.toString(),image.getHeroId(),booleanCallBack);
                }))
                .addOnFailureListener(e -> Log.e("Firebase", "Ошибка загрузки", e));
    }

    @Override
    public void observeNewsPreview(NewsPreviewCallback callback) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String id=snapshot.getKey();
                    String name=snapshot.child(HERO_NAME).getValue(String.class);
                    String info=snapshot.child(HERO_INFO).getValue(String.class);
                    String avatar=snapshot.child(HERO_AVATAR_IMAGE).getValue(String.class);

                    callback.getNewsPreview(new NewsPreviewItem(name,id,info,avatar,"HERO"));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void saveHeroImageAvatarUrl(String imageId, String heroId, BooleanCallBack booleanCallBack) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(DATABASE_WITH_HEROES_DATA).child(heroId).child(HERO_AVATAR_IMAGE);
        if (imageId != null) {
            databaseRef.setValue(imageId);
            booleanCallBack.getBoolean(true);
        }
    }
}