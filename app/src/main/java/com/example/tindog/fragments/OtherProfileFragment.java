package com.example.tindog.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.Dog;

public class OtherProfileFragment extends Fragment {

    private Dog dog;
    private ImageView profileImg;
    private TextView profileTitle;
    private TextView owner;
    private TextView phone;
    private TextView location;
    private TextView weight;
    private TextView profileDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dog = OtherProfileFragmentArgs.fromBundle(getArguments()).getDog();
        profileImg = view.findViewById(R.id.otherProfileImg);
        profileTitle = view.findViewById(R.id.otherProfileTitle);
        owner = view.findViewById(R.id.otherProfileOwner);
        phone = view.findViewById(R.id.otherProfilePhone);
        location = view.findViewById(R.id.otherProfileLocation);
        weight = view.findViewById(R.id.otherProfileWeight);
        profileDescription = view.findViewById(R.id.otherProfileDescription);

        Glide.with(this).load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImg);
        profileTitle.setText(dog.getName() + ", " + dog.getAge() + " years old, " + dog.getBreed());
        owner.setText("Owner: " + dog.getOwnerName());
        phone.setText("Phone: " + dog.getOwnersPhone());
        location.setText("Location: " + dog.getLocation());
        weight.setText("Weight: " + dog.getWeight());
        profileDescription.setText(dog.getDescription());
    }
}