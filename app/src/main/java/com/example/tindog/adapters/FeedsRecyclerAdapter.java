package com.example.tindog.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tindog.R;
import com.example.tindog.interfaces.OnItemClickListener;
import com.example.tindog.models.Dog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedsRecyclerAdapter extends RecyclerView.Adapter<FeedsRecyclerAdapter.FeedHolder> {
    private final List<Dog> dogs = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs.clear();
        this.dogs.addAll(dogs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);
        return new FeedHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        Dog dog = dogs.get(position);
        Glide.with(holder.itemView.getContext()).load(dog.getDogImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.dogImg);
        holder.dogDetails.setText(dog.getName() + ", " + dog.getAge() + " years old, " + dog.getBreed());
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        private final CircleImageView dogImg;
        private final TextView dogDetails;
        private final Button profileBtn;

        public FeedHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            dogImg = itemView.findViewById(R.id.feedDogImage);
            dogDetails = itemView.findViewById(R.id.feedDogDetails);
            profileBtn = itemView.findViewById(R.id.feedProfileBtn);
            profileBtn.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        onItemClickListener.onItemClick(dogs.get(position), position);
                }
            });
        }
    }
}


