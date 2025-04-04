package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.models.MapModule;

import java.util.List;

public interface MapSqliteRepository{
    void fetchDataFromFirebase(MapModulesCallback mapModulesCallback);
    void saveModulesToDatabase(List<MapModule> modules);
    List<MapModule> getModulesFromDatabase();
}
