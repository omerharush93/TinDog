package com.example.tindog.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tindog.R;
import com.example.tindog.models.Dog;
import com.example.tindog.models.ModelFirebase;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tindog.App.uriToBitmap;

public class RegisterFragment extends Fragment {

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
    private Button registerBtn;
    private final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInput = view.findViewById(R.id.register_email);
        passwordInput = view.findViewById(R.id.register_password);
        dogName = view.findViewById(R.id.register_dog_name);
        ownerName = view.findViewById(R.id.register_owner_name);
        ownerPhone = view.findViewById(R.id.register_phone);
        location = view.findViewById(R.id.register_location);
        breedSpinnerInit();
        ageSpinnerInit();
        weightSpinnerInit();
        dogImage = view.findViewById(R.id.register_dog_image);
        dogImage.setOnClickListener(v -> chooseImageFromGallery());
        description = view.findViewById(R.id.register_dog_description);
        registerBtn = view.findViewById(R.id.register_fragment_create_btn);
        registerBtn.setOnClickListener(v -> createDogProfile());
    }

    private void breedSpinnerInit() {
        breedSpinner = getView().findViewById(R.id.breed_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);
    }

    private void ageSpinnerInit() {
        ageSpinner = getView().findViewById(R.id.age_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_age_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
    }


    private void weightSpinnerInit() {

        weightSpinner = getView().findViewById(R.id.weight_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dogs_weight_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
    }

    public void chooseImageFromGallery() {

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
                dogImage.setImageURI(data.getData());
                dogImageBitmap = uriToBitmap(data.getData());
            }
        } else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void createDogProfile() {
        if (dogName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter dog name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ownerName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter owner name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ownerPhone.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter owner phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dogImageBitmap == null) {
            Toast.makeText(getContext(), "Please enter dog image", Toast.LENGTH_SHORT).show();
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
                                Navigation.findNavController(getView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToFeedsFragment());
                                getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });
    }
}