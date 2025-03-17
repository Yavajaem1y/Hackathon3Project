package com.androidlesson.data.main;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageFirebaseRepository {
    private FirebaseDatabase firebaseDatabase;

    private final String DATABASE_WITH_HEROES_DATA="DATABASE_WITH_HEROES_DATA";
    private final String HERO_ADDITIONAL_IMAGES="heroAdditionalImages";

    public ImageFirebaseRepository() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void setHeroAdditionalImages(List<Uri> uriList, String heroId, BooleanCallBack booleanCallBack) {
        Log.d("ImageRepository", "Size " + uriList.size());
        if (uriList.isEmpty()) {
            booleanCallBack.getBoolean(true);  // Если список пуст, сразу вызываем колбэк
        } else {
            List<String> heroAdditionalImages = new ArrayList<>();
            AtomicInteger remainingTasks = new AtomicInteger(uriList.size());  // Счётчик оставшихся задач

            // Для каждого изображения в списке
            for (int i = 0; i < uriList.size(); i++) {
                Uri uri = uriList.get(i);
                if (uri != null) {
                    Log.d("ImageRepository", i + " image start load");

                    String imageId = "images/heroesAdditionalImages/" + heroId + i + ".jpg";
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageId);

                    storageRef.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                Log.d("ImageRepository", "image is loaded");
                                heroAdditionalImages.add(downloadUri.toString());

                                // Проверяем, если все изображения загружены
                                if (remainingTasks.decrementAndGet() == 0) {
                                    // Когда все изображения загружены, сохраняем их в Firebase
                                    firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA)
                                            .child(heroId)
                                            .child(HERO_ADDITIONAL_IMAGES)
                                            .setValue(heroAdditionalImages)
                                            .addOnCompleteListener(task -> {
                                                Log.d("ImageRepository", "Current images size: " + heroAdditionalImages.size());
                                                booleanCallBack.getBoolean(task.isSuccessful());
                                            });
                                }
                            }
                        });
                    }).addOnFailureListener(e -> {
                        Log.d("ImageRepository", "Image upload failed: " + e.getMessage());
                        // Возможно, обработать ошибку
                    });
                } else {
                    // Если URI == null, просто уменьшаем счетчик задач, но не добавляем изображение
                    if (remainingTasks.decrementAndGet() == 0) {
                        // Если это был последний элемент в списке, отправляем пустой список в Firebase
                        firebaseDatabase.getReference(DATABASE_WITH_HEROES_DATA)
                                .child(heroId)
                                .child(HERO_ADDITIONAL_IMAGES)
                                .setValue(heroAdditionalImages)
                                .addOnCompleteListener(task -> {
                                    Log.d("ImageRepository", "Current images size: " + heroAdditionalImages.size());
                                    booleanCallBack.getBoolean(task.isSuccessful());
                                });
                    }
                }
            }
        }
    }



}
