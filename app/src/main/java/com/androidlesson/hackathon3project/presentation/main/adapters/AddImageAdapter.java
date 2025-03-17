package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.hackathon3project.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.AddImageViewHolder>{

    private List<Uri> images;
    private OnAddImageClickListener onAddImageClickListener;

    public AddImageAdapter(List<Uri> images, OnAddImageClickListener onAddImageClickListener) {
        this.images = images;
        this.onAddImageClickListener = onAddImageClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewImages(List<Uri> images){
        this.images = images;
        this.images.add(null);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        Uri data = images.get(position);
        return data == null ? R.layout.item_add_image_preview : R.layout.item_image_preview;
    }

    @NonNull
    @Override
    public AddImageAdapter.AddImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new AddImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddImageAdapter.AddImageViewHolder holder, int position) {
        Uri data = images.get(position);

        if (data != null) {
            Glide.with(holder.itemView.getContext()).load(data).into(holder.addImageButton);
        }

        holder.itemView.setOnClickListener(v -> {
            if (data == null) {
                onAddImageClickListener.onAddImageClick();
            } else {
                // Если hero != null, TODO: выполнить необходимое действие
                // TODO: Добавить действия, которые нужно выполнить при клике
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class AddImageViewHolder extends RecyclerView.ViewHolder{
        ImageView addImageButton;

        public AddImageViewHolder(@NonNull View itemView) {
            super(itemView);
            addImageButton = itemView.findViewById(R.id.iv_hero_image);
        }
    }

    public interface OnAddImageClickListener {
        void onAddImageClick();
    }
}
