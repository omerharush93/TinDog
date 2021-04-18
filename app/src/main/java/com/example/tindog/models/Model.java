package com.example.tindog.models;

public class Model {
    public final static Model instance = new Model();

    private Model() {

    }

    public interface Listener<T> {
        void onComplete(T data);
    }

}