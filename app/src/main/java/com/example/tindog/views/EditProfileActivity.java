package com.example.tindog.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.Dog;

public class EditProfileActivity extends AppCompatActivity {
    private Dog dog;
    private ImageView profileImg;
    private EditText profileName;
    private Spinner breedSpinner;
    private Spinner ageSpinner;
    private Spinner weightSpinner;
    private EditText profileOwner;
    private EditText profilePhone;
    private EditText profileLocation;
    private EditText profileDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dog = (Dog) getIntent().getExtras().get("dog");
        profileImg = findViewById(R.id.editProfileImg);
        profileName = findViewById(R.id.editProfileName);
        profileOwner = findViewById(R.id.editProfileOwner);
        profilePhone = findViewById(R.id.editProfilePhone);
        profileLocation = findViewById(R.id.editProfileLocation);
        profileDescription = findViewById(R.id.editProfileDescription);

        breedSpinnerInit();
        ageSpinnerInit();
        weightSpinnerInit();

        Glide.with(this).load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImg);
        profileName.setText(dog.getName());
        profileOwner.setText(dog.getOwnerName());
        profilePhone.setText(dog.getOwnersPhone());
        profileLocation.setText(dog.getLocation());
        profileDescription.setText(dog.getDescription());
    }

    private void breedSpinnerInit() {
        String[] strings = getResources().getStringArray(R.array.dogs_array);
        breedSpinner = findViewById(R.id.editProfileBreed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);
        for (int i = 0; i < strings.length; i++) {
            if (dog.getBreed().contentEquals(strings[i]))
                breedSpinner.setSelection(i);
        }
    }

    private void ageSpinnerInit() {
        ageSpinner = findViewById(R.id.editProfileAge);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_age_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
        ageSpinner.setSelection(dog.getAge());
    }


    private void weightSpinnerInit() {
        weightSpinner = findViewById(R.id.editProfileWeight);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_weight_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
        weightSpinner.setSelection(dog.getWeight());
    }
}