package com.example.tindog.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Dog implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String ownerName;
    private String ownersPhone;
    private String location;
    private String breed;
    private int age;
    private int weight;
    private String dogImgUrl;
    private String description;

    public Dog() {
        this.id = "";
        this.name = "";
        this.ownerName = "";
        this.ownersPhone = "";
        this.location = "";
        this.breed = "";
        this.age = 1;
        this.weight = 1;
        this.dogImgUrl = "";
        this.description = "";
    }

    public Dog(@NonNull String id, String name, String ownerName, String ownersPhone, String location, String breed, int age, int weight, String dogImgUrl, String description) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.ownersPhone = ownersPhone;
        this.location = location;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.dogImgUrl = dogImgUrl;
        this.description = description;
    }


    //******geters*****//
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnersPhone() {
        return ownersPhone;
    }

    public String getLocation() {
        return location;
    }

    public int getWeight() {
        return weight;
    }

    public String getDogImgUrl() {
        return dogImgUrl;
    }

    public String getDescription() {
        return description;
    }


    //******seters*****//
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnersPhone(String ownersPhone) {
        this.ownersPhone = ownersPhone;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }


    public void setDogImgUrl(String dogImgUrl) {
        this.dogImgUrl = dogImgUrl;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
