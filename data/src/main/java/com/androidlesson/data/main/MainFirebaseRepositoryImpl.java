package com.androidlesson.data.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.EventDataCallback;
import com.androidlesson.domain.main.interfaces.FavoriteRecordCallback;
import com.androidlesson.domain.main.interfaces.HeroDataCallback;
import com.androidlesson.domain.main.interfaces.HeroDataPreviewCallback;
import com.androidlesson.domain.main.interfaces.ListStringsCallback;
import com.androidlesson.domain.main.interfaces.NewsPreviewCallback;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.domain.main.models.FavoriteRecord;
import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroImageToDb;
import com.androidlesson.domain.main.models.HeroItemPreview;
import com.androidlesson.domain.main.models.NewsEventPreviewItem;
import com.androidlesson.domain.main.models.NewsHeroPreviewItem;
import com.androidlesson.domain.main.models.ProudOnEventModel;
import com.androidlesson.domain.main.models.ProudOnHeroModel;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    private final String DATABASE_WITH_USERS_DATA ="USERS_DATA_DATABASE";
    private final String DATABASE_SYSTEM_ID_TO_APP_ID="DATABASE_SYSTEM_ID_TO_APP_ID";
    private final String DATABASE_WITH_HEROES_DATA="DATABASE_WITH_HEROES_DATA";
    private final String DATABASE_WITH_EVENTS_DATA="DATABASE_WITH_EVENTS_DATA";

    private final String USER_SYSTEM_ID="userSystemId";
    private final String USER_ID="userId";
    private final String USER_LIST_HEROES_IDS="listHeroIds";
    private final String USER_LIST_FAVORITE_RECORDS_IDS="listFavoriteRecordIds";
    private final String HERO_NAME="heroName";
    private final String HERO_INFO="heroInfo";
    private final String HERO_DATE="heroDate";
    private final String HERO_AVATAR_IMAGE="heroAvatarImage";
    private final String HERO_ADDITIONAL_IMAGES="heroAdditionalImages";
    private final String HERO_LIST_PROUD="listProud";
    private final String EVENT_AVATAR_IMAGE="eventAvatarImage";
    private final String EVENT_DATE="eventDate";
    private final String EVENT_INFO="eventInfo";
    private final String EVENT_NAME="eventName";
    private final String EVENT_PREVIEW_INFO="eventPreviewInfo";
    private final String EVENT_PROUD_LIST="eventProudList";


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
                .setValue(new HeroDataToDb.InitialHero(heroDataToDb.getHeroName(),heroDataToDb.getHeroInfo(),heroDataToDb.getListProud(),heroDataToDb.getHeroDate()))
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
        firebaseDatabase.getReference(DATABASE_WITH_EVENTS_DATA).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String id=snapshot.getKey();
                    String name=snapshot.child(EVENT_NAME).getValue(String.class);
                    String date=snapshot.child(EVENT_DATE).getValue(String.class);
                    String avatar=snapshot.child(EVENT_AVATAR_IMAGE).getValue(String.class);
                    String info=snapshot.child(EVENT_PREVIEW_INFO).getValue(String.class);

                    callback.getEventPreview(new NewsEventPreviewItem(name,id,date,info,avatar));
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

        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String id=snapshot.getKey();
                    String name=snapshot.child(HERO_NAME).getValue(String.class);
                    String date=snapshot.child(HERO_DATE).getValue(String.class);
                    String avatar=snapshot.child(HERO_AVATAR_IMAGE).getValue(String.class);

                    callback.getHeroPreview(new NewsHeroPreviewItem(name,id,date,avatar));
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

    Set<String> loadedHeroes = new HashSet<>();

    @Override
    public void observeCurrentUserHeroesPreview(String id, HeroDataPreviewCallback heroDataPreviewCallback) {
        Log.d("HeroAdapter", id);
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(id).child(USER_LIST_HEROES_IDS)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String heroId = snapshot.getValue(String.class);
                        if (heroId == null || loadedHeroes.contains(heroId)) {
                            return;
                        }
                        loadedHeroes.add(heroId);

                        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(heroId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Log.d("HeroAdapter", snapshot.getKey());
                                            String date=snapshot.child(HERO_DATE).getValue(String.class);
                                            String name = snapshot.child(HERO_NAME).getValue(String.class);
                                            String info = snapshot.child(HERO_INFO).getValue(String.class);
                                            String avatar = snapshot.child(HERO_AVATAR_IMAGE).getValue(String.class);
                                            String id = snapshot.getKey();
                                            heroDataPreviewCallback.getHeroDataPreview(new HeroItemPreview(id, name, avatar,info,date));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    @Override
    public void observeHeroData(String heroId, HeroDataCallback heroDataCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(heroId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    HeroData data=snapshot.getValue(HeroData.class);
                    if (data.getListProud()==null) data.setListProud(new ArrayList<>());
                    heroDataCallback.getHeroData(data);
                }
                else {
                    heroDataCallback.getHeroData(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void proudHero(ProudOnHeroModel proudOnHeroModel) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(proudOnHeroModel.getHeroId()).child(HERO_LIST_PROUD).setValue(proudOnHeroModel.getListProud());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(proudOnHeroModel.getUserId()).child(USER_LIST_FAVORITE_RECORDS_IDS).setValue(proudOnHeroModel.getListFavoriteRecordIds());
    }

    @Override
    public void deleteHeroData(String heroId, UserData userData, BooleanCallBack booleanCallBack) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(heroId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userData.getUserId()).child(USER_LIST_HEROES_IDS).setValue(userData.getListHeroIds());
                    booleanCallBack.getBoolean(true);
                }
            }
        });
    }

    @Override
    public void getHeroProudList(String heroId, ListStringsCallback listStringsCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(heroId).child(HERO_LIST_PROUD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> list = snapshot.getValue(t);
                    if (list == null) list = new ArrayList<>();
                    listStringsCallback.getList(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void observeEventData(String eventId, EventDataCallback eventDataCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_EVENTS_DATA).child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String date = snapshot.child(EVENT_DATE).getValue(String.class);
                    String name = snapshot.child(EVENT_NAME).getValue(String.class);
                    String info = snapshot.child(EVENT_INFO).getValue(String.class);
                    String avatar = snapshot.child(EVENT_AVATAR_IMAGE).getValue(String.class);

                    // Используем GenericTypeIndicator для списка
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    List<String> list = snapshot.child(EVENT_PROUD_LIST).getValue(t);

                    eventDataCallback.getEventDate(new EventDataFromDB(date, name, info, avatar, list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }


    @Override
    public void proudEvent(ProudOnEventModel model) {
        firebaseDatabase.getReference(DATABASE_WITH_EVENTS_DATA).child(model.getEventId()).child(EVENT_PROUD_LIST).setValue(model.getListProud());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(model.getUserId()).child(USER_LIST_FAVORITE_RECORDS_IDS).setValue(model.getListFavoriteRecordIds());
    }

    @Override
    public void getHeroDataById(String heroId, FavoriteRecordCallback favoriteRecordCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA).child(heroId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name=snapshot.child(HERO_NAME).getValue(String.class);
                    String info=snapshot.child(HERO_INFO).getValue(String.class);
                    String image=snapshot.child(HERO_AVATAR_IMAGE).getValue(String.class);
                    FavoriteRecord data=new FavoriteRecord();
                    data.setInfo(info);
                    data.setName(name);
                    data.setId(heroId);
                    data.setAvatarUrl(image);
                    data.setType("HERO");
                    favoriteRecordCallback.getFavoriteRecord(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getEventDataById(String eventId, FavoriteRecordCallback favoriteRecordCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_EVENTS_DATA).child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name=snapshot.child(EVENT_NAME).getValue(String.class);
                    String info=snapshot.child(EVENT_INFO).getValue(String.class);
                    String image=snapshot.child(EVENT_AVATAR_IMAGE).getValue(String.class);
                    FavoriteRecord data=new FavoriteRecord();
                    data.setInfo(info);
                    data.setName(name);
                    data.setId(eventId);
                    data.setAvatarUrl(image);
                    data.setType("EVENT");
                    favoriteRecordCallback.getFavoriteRecord(data);
                }
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