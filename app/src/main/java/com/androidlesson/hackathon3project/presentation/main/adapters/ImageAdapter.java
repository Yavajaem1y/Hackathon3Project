package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.hackathon3project.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{

    private List<String> images=new ArrayList<>();
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImages(List<String> images){
        this.images=images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ImageAdapter.ImageHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_image_preview;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        String image=images.get(position);
        if (image!=null && !image.isEmpty()){
            Glide.with(context).load(image).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.iv_hero_image);
        }
    }
}
