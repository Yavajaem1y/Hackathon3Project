package com.androidlesson.data.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidlesson.domain.main.interfaces.MapModulesCallback;
import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.repository.MapSqliteRepository;

import java.util.Collections;
import java.util.List;

public class MapSqliteRepositoryImpl extends SQLiteOpenHelper implements MapSqliteRepository{
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 1;

    public MapSqliteRepositoryImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE modules (id INTEGER PRIMARY KEY, name TEXT)");
        db.execSQL("CREATE TABLE points (id INTEGER PRIMARY KEY, module_id INTEGER, title TEXT, is_open INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS modules");
        db.execSQL("DROP TABLE IF EXISTS points");
        onCreate(db);
    }

    @Override
    public void fetchDataFromFirebase(MapModulesCallback mapModulesCallback) {

    }

    @Override
    public void saveModulesToDatabase(List<MapModule> modules) {

    }

    @Override
    public List<MapModule> getModulesFromDatabase() {
        return Collections.emptyList();
    }
}
