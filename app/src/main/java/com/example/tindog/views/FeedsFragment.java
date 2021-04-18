package com.example.tindog.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tindog.R;
import com.example.tindog.adapters.FeedsRecyclerAdapter;
import com.example.tindog.models.Dog;

import java.util.ArrayList;

public class FeedsFragment extends Fragment {

    private RecyclerView feedsRecycler;
    private FeedsRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedsRecycler = view.findViewById(R.id.feedsRecyclerView);
        feedsRecycler.setHasFixedSize(true);
        adapter = new FeedsRecyclerAdapter();
        feedsRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener((o, position) -> {
            Toast.makeText(getContext(), ((Dog) o).getAge() + "", Toast.LENGTH_SHORT).show();
        });
        ArrayList<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());
        dogs.add(new Dog());
        dogs.add(new Dog());
        dogs.add(new Dog());
        dogs.add(new Dog());
        adapter.setDogs(dogs);

    }
}