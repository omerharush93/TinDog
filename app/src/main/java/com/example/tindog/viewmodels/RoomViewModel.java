package com.example.tindog.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tindog.models.Dog;
import com.example.tindog.room.DogDao;
import com.example.tindog.room.DogDatabase;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private final DogDao dogDao;

    public RoomViewModel(Application application) {
        super(application);
        this.dogDao = DogDatabase.getInstance(application.getApplicationContext()).dogDao();
    }

    public void insert(Dog dog) {
        new Thread(() -> dogDao.insertDog(dog)).start();
    }

    public LiveData<List<Dog>> getAllDogs() {
        return dogDao.getAllDogs();
    }

    public void deleteAllDogs() {
        new Thread(dogDao::deleteAllDogs).start();
    }

}
