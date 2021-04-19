package com.example.tindog.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.models.Dog;
import com.example.tindog.models.ModelFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ImageView dogImg;
    private TextView title;
    private TextView owner;
    private TextView phone;
    private TextView location;
    private TextView weight;
    private TextView description;
    private ImageView editBtn;
    private Button logoutBtn;
    private Dog dog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogImg = view.findViewById(R.id.profileImg);
        title = view.findViewById(R.id.profileTitle);
        owner = view.findViewById(R.id.profileOwner);
        phone = view.findViewById(R.id.profilePhone);
        location = view.findViewById(R.id.profileLocation);
        weight = view.findViewById(R.id.profileWeight);
        description = view.findViewById(R.id.profileDescription);
        editBtn = view.findViewById(R.id.profileEditBtn);
        logoutBtn = view.findViewById(R.id.profileLogoutBtn);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            intent.putExtra("dog", dog);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v->{
            ModelFirebase.signOut();
            getActivity().recreate();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ModelFirebase.getDogFromDB(FirebaseAuth.getInstance().getCurrentUser().getUid(), listener -> {
            if (listener != null) {
                dog = listener;
                Glide.with(getContext()).asBitmap().load(listener.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(dogImg);
                title.setText(dog.getName() + ", " + dog.getAge() + " years old, " + dog.getBreed());
                owner.setText("Owner: " + dog.getOwnerName());
                phone.setText("Phone: " + dog.getOwnersPhone());
                location.setText("Location: " + dog.getLocation());
                weight.setText("Weight: " + dog.getWeight());
                description.setText(listener.getDescription());
            }
        });
    }
}