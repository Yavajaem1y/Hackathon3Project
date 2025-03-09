package com.androidlesson.data.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.repository.MainFirebaseRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFirebaseRepositoryImpl implements MainFirebaseRepository {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    private final String DATABASE_WITH_USERS_DATA ="USERS_DATA_DATABASE";
    private final String DATABASE_SYSTEM_ID_TO_APP_ID="DATABASE_SYSTEM_ID_TO_APP_ID";

    private final String USER_SYSTEM_ID="userSystemId";
    private final String USER_ID="userId";
    private final String USER_NAME="userName";
    private final String USER_SURNAME="userSurname";
    private final String ID_LAST_KEY="LAST_KEY";

    private String userSystemId;

    public MainFirebaseRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userSystemId=auth.getUid();
    }

    @Override
    public void observeCurrentUserData(String userId,UserDataCallback userDataCallback) {
        Log.d("MainRep",userId.toString());
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserData userData=snapshot.getValue(UserData.class);
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
