package com.example.tindog.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tindog.R;
import com.example.tindog.adapters.FeedsRecyclerAdapter;
import com.example.tindog.models.Dog;
import com.example.tindog.viewmodels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class FeedsFragment extends Fragment {

    private RecyclerView feedsRecycler;
    private FeedsRecyclerAdapter adapter;
    private List<Dog> dogs = new ArrayList<>();
    private RoomViewModel roomViewModel;

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
            Intent intent = new Intent(getContext(), OtherProfileActivity.class);
            intent.putExtra("dog", (Dog) o);
            startActivity(intent);
        });
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        roomViewModel.getAllDogs().observe(getViewLifecycleOwner(), obs -> {
            dogs = obs;
            adapter.setDogs(dogs);
        });
    }
}