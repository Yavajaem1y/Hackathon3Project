package com.androidlesson.data.authorization;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.repository.AuthorizationFirebaseRepository;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class AuthorizationFirebaseRepositoryImpl implements AuthorizationFirebaseRepository {

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

    public AuthorizationFirebaseRepositoryImpl() {
        this.auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userSystemId=auth.getUid();
    }

    @Override
    public void registration(DataToRegistration dataToRegistration, BooleanCallBack booleanCallBack, StringCallBack errorCallback) {
        auth.createUserWithEmailAndPassword(dataToRegistration.getEmail(),dataToRegistration.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userSystemId=auth.getUid();
                    booleanCallBack.getBoolean(true);
                }
                else {
                    errorCallback.getString("Аккаунт с таким email уже существует");
                    booleanCallBack.getBoolean(false);
                }
            }
        });
    }

    @Override
    public void login(DataToLogin dataToLogin, BooleanCallBack booleanCallBack, StringCallBack errorCallback) {
        auth.signInWithEmailAndPassword(dataToLogin.getEmail(),dataToLogin.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Authorization","Login is successful");
                    userSystemId=auth.getUid();
                    booleanCallBack.getBoolean(true);
                }
                else {
                    errorCallback.getString("Неверный email или пароль");
                    booleanCallBack.getBoolean(false);
                }
            }
        });
    }

    @Override
    public void checkAvailableIds(String id, BooleanCallBack booleanCallBack) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if (!child.getKey().equals(ID_LAST_KEY)) {
                            String idFromDb = child.child(USER_ID).getValue(String.class);
                            if (idFromDb != null && id.equals(idFromDb)) {
                                booleanCallBack.getBoolean(true);
                                return;
                            }
                        }
                    }
                    booleanCallBack.getBoolean(false);
                } else {
                    booleanCallBack.getBoolean(false);
                }
            } else {
                booleanCallBack.getBoolean(false);
            }
        });
    }

    String userId;
    @Override
    public void saveUserData(UserData userData, UserDataCallback userDataCallback) {
        Log.d("User id to db",userData.getUserId());
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).child(USER_ID).setValue(userData.getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userId=userData.getUserId();
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).setValue(new UserDataToDB(userSystemId,userData.getUserName(),userData.getUserSurname())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userDataCallback.getUserData(new UserData(userData.getUserName(),userData.getUserSurname(),userSystemId,userId));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getBasicId(StringCallBack callbackWithId) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID)
                .child(ID_LAST_KEY)
                .child(USER_ID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            Integer currentId = snapshot.getValue(Integer.class);
                            if (currentId != null) {
                                int newId = currentId + 1;
                                callbackWithId.getString(String.valueOf(newId));

                                firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID)
                                        .child("LAST_KEY")
                                        .child(USER_ID)
                                        .setValue(newId)
                                        .addOnFailureListener(e -> {
                                            Log.e("Firebase", "Ошибка при обновлении id: ", e);
                                        });
                            } else {
                                callbackWithId.getString("1");
                            }
                        } else {
                            callbackWithId.getString("1");
                        }
                    } else {
                        Log.e("Firebase", "Ошибка при получении данных: ", task.getException());
                    }
                });
    }

    @Override
    public void getCurrentUserData(UserDataCallback userDataCallback) {
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID)
                .child(auth.getUid())
                .child(USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String userId = dataSnapshot.getValue(String.class);
                            if (userId != null) {
                                firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA)
                                        .child(userId)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                            @Override
                                            public void onSuccess(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    UserData userData = dataSnapshot.getValue(UserData.class);
                                                    if (userData != null) {
                                                        userData.setUserId(dataSnapshot.getKey());
                                                        userDataCallback.getUserData(userData);
                                                    } else {
                                                        userDataCallback.getUserData(null);
                                                    }
                                                } else {
                                                    userDataCallback.getUserData(null);
                                                }
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            userDataCallback.getUserData(null);
                                        });
                            } else {
                                userDataCallback.getUserData(null);
                            }
                        } else {
                            userDataCallback.getUserData(null);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    userDataCallback.getUserData(null);
                });
    }

    @Override
    public void logOut() {
        auth.signOut();
        Log.d("Authorization","SignOut in fb");
    }

}
