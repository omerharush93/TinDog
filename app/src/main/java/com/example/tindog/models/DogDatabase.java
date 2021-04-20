package com.example.tindog.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Dog.class}, version = 1)
public abstract class DogDatabase extends RoomDatabase {

    private static DogDatabase instance;

    public abstract DogDao dogDao();

    public static synchronized DogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DogDatabase.class, "dog_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
