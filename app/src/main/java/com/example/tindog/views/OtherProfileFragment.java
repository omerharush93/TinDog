package com.example.tindog.views;

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
        profileDescription = view.findViewById(R.id.otherProfileDescription);

        Glide.with(this).load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImg);
        profileTitle.setText(dog.getName() + ", " + dog.getAge() + " years old, " + dog.getBreed());
        profileDescription.setText(dog.getDescription());
    }
}