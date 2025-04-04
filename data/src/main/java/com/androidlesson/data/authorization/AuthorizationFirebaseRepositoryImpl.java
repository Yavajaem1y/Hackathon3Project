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
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToDB;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

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
    private final String USER_AVATAR_IMAGE="imageData";

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
        firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(userSystemId).child(USER_ID).setValue(userData.getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userId=userData.getUserId();
                    UserDataToDB data=new UserDataToDB(userSystemId,userData.getUserName(),userData.getUserSurname());
                    data.setCurrentPoint(1);
                    data.setIsFirstTime(1);
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                                        if (userData.getListFavoriteRecordIds()==null) {
                                                            userData.setListFavoriteRecordIds(new ArrayList<>());
                                                        }
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

    @Override
    public void editEmail(String email, BooleanCallBack booleanCallBack) {
        //FirebaseUser user = auth.getCurrentUser();
        //if (user != null) {
        //    AuthCredential credential = EmailAuthProvider.getCredential();
        //    user.reauthenticate(credential).addOnCompleteListener(task -> {
        //        if (task.isSuccessful()) {
        //            user.updateEmail(email).addOnCompleteListener(updateTask -> {
        //                booleanCallBack.getBoolean(updateTask.isSuccessful());
        //            });
        //        } else {
        //            booleanCallBack.getBoolean(false);
        //        }
        //    });
        //} else {
        //    booleanCallBack.getBoolean(false);
        //}
        booleanCallBack.getBoolean(true);
    }

    @Override
    public void editId(String oldId, String newId, BooleanCallBack booleanCallBack) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(oldId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserData data=snapshot.getValue(UserData.class);

                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(newId).setValue(data)
                            .addOnSuccessListener(aVoid -> {
                                firebaseDatabase.getReference(DATABASE_SYSTEM_ID_TO_APP_ID).child(auth.getUid()).child(USER_ID).setValue(newId);
                                firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(oldId).removeValue();
                                booleanCallBack.getBoolean(true);
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void editUserData(UserDataToEdit userDataToEdit, UserDataCallback userDataCallback) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userDataToEdit.getUserId()).child(USER_NAME).setValue(userDataToEdit.getUserName()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userDataToEdit.getUserId()).child(USER_SURNAME).setValue(userDataToEdit.getUserSurname()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                getCurrentUserData(userDataCallback);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getUserEmail(StringCallBack stringCallBack) {
        stringCallBack.getString(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
    }

    @Override
    public void addImageAvatar(ImageToDb imageToDb, BooleanCallBack booleanCallBack) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageToDb.getImageId());
        storageRef.putBytes(imageToDb.getImageData())
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveImageAvatarUrl(uri.toString(),imageToDb.getUserId(),booleanCallBack);
                }))
                .addOnFailureListener(e -> Log.e("Firebase", "Ошибка загрузки", e));
    }

    @Override
    public void setIsFirstTime(UserData userData) {
        firebaseDatabase.getReference(DATABASE_WITH_USERS_DATA).child(userData.getUserId()).child("isFirstTime").setValue(0);
    }

    private void saveImageAvatarUrl(String imageId, String userId, BooleanCallBack booleanCallBack) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child(DATABASE_WITH_USERS_DATA).child(userId).child(USER_AVATAR_IMAGE);
        if (imageId != null) {
            databaseRef.setValue(imageId);
            booleanCallBack.getBoolean(true);
        }
    }

}
