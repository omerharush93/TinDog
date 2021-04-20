package com.example.tindog.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tindog.R;
import com.example.tindog.models.FeedsRecyclerAdapter;
import com.example.tindog.models.Dog;
import com.example.tindog.models.ModelFirebase;
import com.example.tindog.models.RoomViewModel;
import com.google.firebase.auth.FirebaseAuth;

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
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Navigation.findNavController(view).navigate(FeedsFragmentDirections.actionFeedsFragmentToLoginFragment());
            return;
        }
        roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);
        roomViewModel.deleteAllDogs();
        ModelFirebase.getAllDogsFromDB(dogs -> {
            if (dogs != null) {
                for (Dog dog : dogs) {
                    roomViewModel.insert(dog);
                }
            }
        });
        feedsRecycler = view.findViewById(R.id.feedsRecyclerView);
        feedsRecycler.setHasFixedSize(true);
        adapter = new FeedsRecyclerAdapter();
        feedsRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener((o, position) -> {
            Navigation.findNavController(view).navigate(FeedsFragmentDirections.actionFeedsFragmentToOtherProfileFragment((Dog) o));
        });
        roomViewModel.getAllDogs().observe(getViewLifecycleOwner(), obs -> {
            dogs = obs;
            adapter.setDogs(dogs);
        });
    }
}