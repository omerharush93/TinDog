package com.example.tindog.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.Dog;
import com.example.tindog.models.ModelFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.tindog.utils.App.uriToBitmap;

public class EditProfileFragment extends Fragment {
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
    private FloatingActionButton confirmBtn;
    private Bitmap dogImageBitmap = null;
    private final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dog = (Dog) EditProfileFragmentArgs.fromBundle(getArguments()).getDog();
        profileImg = view.findViewById(R.id.editProfileImg);
        profileImg.setOnClickListener(v -> chooseFromGallery());
        profileName = view.findViewById(R.id.editProfileName);
        profileOwner = view.findViewById(R.id.editProfileOwner);
        profilePhone = view.findViewById(R.id.editProfilePhone);
        profileLocation = view.findViewById(R.id.editProfileLocation);
        profileDescription = view.findViewById(R.id.editProfileDescription);
        confirmBtn = view.findViewById(R.id.editProfileConfirm);
        confirmBtn.setOnClickListener(v -> updateProfile());

        breedSpinnerInit();
        ageSpinnerInit();
        weightSpinnerInit();

        Glide.with(this).asBitmap().load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImg);
        profileName.setText(dog.getName());
        profileOwner.setText(dog.getOwnerName());
        profilePhone.setText(dog.getOwnersPhone());
        profileLocation.setText(dog.getLocation());
        profileDescription.setText(dog.getDescription());

    }

    private void breedSpinnerInit() {
        String[] strings = getResources().getStringArray(R.array.dogs_array);
        breedSpinner = getView().findViewById(R.id.editProfileBreed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);
        for (int i = 0; i < strings.length; i++) {
            if (dog.getBreed().contentEquals(strings[i]))
                breedSpinner.setSelection(i);
        }
    }

    private void ageSpinnerInit() {
        ageSpinner = getView().findViewById(R.id.editProfileAge);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_age_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
        ageSpinner.setSelection(dog.getAge()-1);
    }


    private void weightSpinnerInit() {
        weightSpinner = getView().findViewById(R.id.editProfileWeight);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_weight_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
        weightSpinner.setSelection(dog.getWeight());
    }

    public void chooseFromGallery() {
        try {
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Edit profile Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                profileImg.setImageURI(data.getData());
                dogImageBitmap = uriToBitmap(data.getData());
            }
        } else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfile() {
        if (profileName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter dog name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (profileOwner.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter owner name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (profilePhone.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter owner phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (profileLocation.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }
        dog.setName(profileName.getText().toString());
        dog.setAge(Integer.parseInt(ageSpinner.getSelectedItem().toString()));
        dog.setBreed(breedSpinner.getSelectedItem().toString());
        dog.setOwnerName(profileOwner.getText().toString());
        dog.setOwnersPhone(profilePhone.getText().toString());
        dog.setLocation(profileLocation.getText().toString());
        dog.setWeight(Integer.parseInt(weightSpinner.getSelectedItem().toString()));
        dog.setDescription(profileDescription.getText().toString());
        if (dogImageBitmap == null) {
            ModelFirebase.updateDogInDB(dog, db -> {
                if (db) {
                    Navigation.findNavController(getView()).navigateUp();
                }
            });
        } else {
            ModelFirebase.uploadImage(dog, dogImageBitmap, data -> {
                ModelFirebase.updateDogInDB(dog, db -> {
                    if (db) {
                        Navigation.findNavController(getView()).navigateUp();
                    }
                });
            });
        }
    }
}