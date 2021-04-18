package com.example.tindog.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tindog.R;
import com.example.tindog.models.Dog;
import com.example.tindog.models.ModelFirebase;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tindog.utils.App.uriToBitmap;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private EditText dogName;
    private EditText ownerName;
    private EditText ownerPhone;
    private EditText location;
    private Spinner breedSpinner;
    private Spinner ageSpinner;
    private Spinner weightSpinner;
    private CircleImageView dogImage;
    private Bitmap dogImageBitmap;
    private EditText description;
    private final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailInput = findViewById(R.id.register_email);
        passwordInput = findViewById(R.id.register_password);
        dogName = findViewById(R.id.register_dog_name);
        ownerName = findViewById(R.id.register_owner_name);
        ownerPhone = findViewById(R.id.register_phone);
        location = findViewById(R.id.register_location);
        breedSpinnerInit();
        ageSpinnerInit();
        weightSpinnerInit();
        dogImage = findViewById(R.id.register_dog_image);
        description = findViewById(R.id.register_dog_description);
    }


    private void breedSpinnerInit() {
        breedSpinner = findViewById(R.id.breed_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);
    }

    private void ageSpinnerInit() {
        ageSpinner = findViewById(R.id.age_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_age_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
    }


    private void weightSpinnerInit() {

        weightSpinner = findViewById(R.id.weight_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dogs_weight_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
    }

    public void chooseImageFromGallery(View view) {

        try {
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Edit profile Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                dogImage.setImageURI(data.getData());
                dogImageBitmap = uriToBitmap(data.getData());
            }
        } else {
            Toast.makeText(getApplicationContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void createDogProfile(View view) {
        if (dogName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter dog name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ownerName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter owner name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ownerPhone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter owner phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dogImageBitmap == null) {
            Toast.makeText(getApplicationContext(), "Please enter dog image", Toast.LENGTH_SHORT).show();
            return;
        }

        Dog dog = new Dog("",
                dogName.getText().toString(),
                ownerName.getText().toString(),
                ownerPhone.getText().toString(),
                location.getText().toString(),
                breedSpinner.getSelectedItem().toString(),
                Integer.parseInt(ageSpinner.getSelectedItem().toString()),
                Integer.parseInt(weightSpinner.getSelectedItem().toString()),
                "",
                description.getText().toString()
        );

        ModelFirebase.createDogProfile(emailInput, passwordInput, dog, listener -> {
            if (listener != null) {
                ModelFirebase.uploadImage(listener, dogImageBitmap, image -> {
                    if (image != null) {
                        ModelFirebase.uploadDogToDB(dog, res -> {
                            if (res) {
                                finishAffinity();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }
}