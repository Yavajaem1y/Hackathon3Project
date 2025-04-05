package com.androidlesson.data.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.models.MapPoint;
import com.androidlesson.domain.main.repository.MapFirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapFirebaseRepositoryImpl implements MapFirebaseRepository {
    private DatabaseReference databaseReference;

    private final String DATABASE_WITH_TRAINING_MODULES="DATABASE_WITH_TRAINING_MODULES";

    public MapFirebaseRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_WITH_TRAINING_MODULES);
    }

    @Override
    public void getModulesFromFirebase(MapModulesCallback callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String name = child.child("name").getValue(String.class);

                        List<MapPoint> points = new ArrayList<>();
                        DataSnapshot pointsSnapshot = child.child("points");

                        if (pointsSnapshot.exists()) {
                            for (DataSnapshot pointSnapshot : pointsSnapshot.getChildren()) {
                                MapPoint point = pointSnapshot.getValue(MapPoint.class);

                                // Применяем фильтрацию и инициализацию списка items
                                List<MapArticleItem> items = new ArrayList<>();
                                DataSnapshot itemsSnapshot = pointSnapshot.child("items");

                                if (itemsSnapshot.exists()) {
                                    for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                        MapArticleItem item = itemSnapshot.getValue(MapArticleItem.class);

                                        // Проверяем, что данные не пустые
                                        if (item != null) {
                                            if (item.getText() != null || item.getImage() != null) {
                                                items.add(item);  // Добавляем только те элементы, которые содержат данные
                                            }
                                        }
                                    }
                                }

                                point.setItems(items);
                                point.setId(Integer.parseInt(pointSnapshot.getKey()));
                                points.add(point);
                            }
                        }

                        MapModule mapModule = new MapModule(child.getKey(), name, points);
                        callback.getMapModule(mapModule);  // Передаем данные через callback
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Ошибка при получении данных из Firebase: " + error.getMessage());
            }
        });
    }

    @Override
    public void UnlockTheNextPointUseCase(String userId, int unlockPointId,int pointsCompleted) {
        FirebaseDatabase.getInstance().getReference("USERS_DATA_DATABASE").child(userId).child("currentPoint").setValue(unlockPointId);
        FirebaseDatabase.getInstance().getReference("USERS_DATA_DATABASE").child(userId).child("pointsCompleted").setValue(pointsCompleted);
    }
}
