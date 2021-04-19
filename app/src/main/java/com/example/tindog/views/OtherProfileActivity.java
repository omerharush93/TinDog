package com.example.tindog.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.Dog;

public class OtherProfileActivity extends AppCompatActivity {
    private Dog dog;
    private ImageView profileImg;
    private TextView profileTitle;
    private TextView profileDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        dog = (Dog) getIntent().getExtras().get("dog");
        profileImg = findViewById(R.id.otherProfileImg);
        profileTitle = findViewById(R.id.otherProfileTitle);
        profileDescription = findViewById(R.id.otherProfileDescription);

        Glide.with(this).load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImg);
        profileTitle.setText(dog.getName() + ", " + dog.getAge() + " years old, " + dog.getBreed());
        profileDescription.setText(dog.getDescription());

    }
}