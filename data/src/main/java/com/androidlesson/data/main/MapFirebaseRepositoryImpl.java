package com.androidlesson.data.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.models.MapPoint;
import com.androidlesson.domain.main.models.Question;
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
import java.util.Objects;

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
                                List<Question> questions = new ArrayList<>();

                                if (!Objects.equals(point.getType(), "TEST")) {
                                    DataSnapshot itemsSnapshot = pointSnapshot.child("items");
                                    if (itemsSnapshot.exists()) {
                                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                            MapArticleItem item = itemSnapshot.getValue(MapArticleItem.class);

                                            // Проверяем, что данные не пустые
                                            if (item != null) {
                                                if (item.getText() != null || item.getImage() != null) {
                                                    items.add(item);
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    DataSnapshot itemsSnapshot = pointSnapshot.child("questions");
                                    if (itemsSnapshot.exists()) {
                                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                            Question item = itemSnapshot.getValue(Question.class);
                                            if (item != null && item.getQuestionText() != null) {
                                                DataSnapshot optionsSnapshot = itemSnapshot.child("options");
                                                List<String> options = new ArrayList<>();
                                                if (optionsSnapshot.exists()) {
                                                    for (DataSnapshot optionsSnap : optionsSnapshot.getChildren()) {
                                                        String option = optionsSnap.getValue(String.class);
                                                        options.add(option);
                                                    }
                                                    item.setOptions(options);
                                                }

                                                questions.add(item);
                                            }
                                        }
                                    }

                                    point.setQuestions(questions);
                                }

                                point.setItems(items);
                                point.setId(Integer.parseInt(pointSnapshot.getKey()));
                                points.add(point);
                            }
                        }

                        MapModule mapModule = new MapModule(child.getKey(), name, points);
                        callback.getMapModule(mapModule);
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

    @Override
    public void AddMedalToUser(String userId, int medalId) {
        DatabaseReference medalsRef = FirebaseDatabase.getInstance()
                .getReference("USERS_DATA_DATABASE")
                .child(userId)
                .child("medalsId");

        medalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Integer> medalsId = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Integer existingMedal = dataSnapshot.getValue(Integer.class);
                        if (existingMedal != null) {
                            medalsId.add(existingMedal);
                        }
                    }
                }

                // Добавляем новый medalId только если его ещё нет
                if (!medalsId.contains(medalId)) {
                    medalsId.add(medalId);
                    medalsRef.setValue(medalsId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AddMedalToUser", "Database error: " + error.getMessage());
            }
        });
    }

}
