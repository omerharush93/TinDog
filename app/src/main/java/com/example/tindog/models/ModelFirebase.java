package com.example.tindog.models;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tindog.utils.App;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ModelFirebase {

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final StorageReference storage = FirebaseStorage.getInstance().getReference();

    public static void createDogProfile(EditText emailInput, EditText passwordInput, Dog dog, Model.Listener<Dog> listener) {
        auth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(App.getContext(), "User Registered...", Toast.LENGTH_SHORT).show();
                dog.setId(auth.getCurrentUser().getUid());
                listener.onComplete(dog);
            } else {
                Toast.makeText(App.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                listener.onComplete(null);
            }
        });
    }

    public static void uploadImage(Dog dog, Bitmap dogImageBitmap, Model.Listener<Dog> listener) {
        Date date = new Date();
        String imageName = dog.getId() + date.getTime();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        dogImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        StorageReference imageRef = storage.child("images").child(imageName);
        imageRef.putBytes(baos.toByteArray()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(App.getContext(), "Image uploaded...", Toast.LENGTH_SHORT).show();
                imageRef.getDownloadUrl().addOnCompleteListener(uri -> {
                    if (uri.isSuccessful()) {
                        dog.setDogImgUrl(uri.getResult().toString());
                        listener.onComplete(dog);
                    } else {
                        Toast.makeText(App.getContext(), uri.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        listener.onComplete(null);
                    }
                });
            } else {
                Toast.makeText(App.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                listener.onComplete(null);
            }
        });
    }

    public static void uploadDogToDB(Dog dog, Model.Listener<Boolean> listener) {
        db.collection("users").document(dog.getId()).set(dog).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(App.getContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                listener.onComplete(true);
            } else {
                Toast.makeText(App.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                listener.onComplete(false);
            }
        });
    }
}