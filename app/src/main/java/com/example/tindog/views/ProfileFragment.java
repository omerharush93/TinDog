package com.example.tindog.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.ModelFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ImageView dogImg;
    private TextView title;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogImg = view.findViewById(R.id.profileImg);
        title = view.findViewById(R.id.profileTitle);
        description = view.findViewById(R.id.profileDescription);
        ModelFirebase.getDogFromDB(FirebaseAuth.getInstance().getCurrentUser().getUid(), listener -> {
            if (listener != null) {
                title.setText(listener.getName() + ", " + listener.getAge() + " years old");
                description.setText(listener.getDescription());
                Glide.with(getContext()).load(listener.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(dogImg);
            }
        });


    }
}