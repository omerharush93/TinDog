package com.example.tindog.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tindog.models.Dog;

import java.util.List;

@Dao
public interface DogDao {

    @Query("select * from Dog")
    LiveData<List<Dog>> getAllDogs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDog(Dog post);

    @Query("delete from Dog")
    void deleteAllDogs();
}
