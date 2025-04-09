package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.FavoriteRecord;
import com.androidlesson.hackathon3project.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteRecordsAdapter extends RecyclerView.Adapter<FavoriteRecordsAdapter.FavoriteRecordViewHolder> {

    private List<FavoriteRecord> favoriteRecords=new ArrayList<>();
    private Context context;

    public FavoriteRecordsAdapter(Context context) {
        this.context=context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFavoriteRecords(List<FavoriteRecord> favoriteRecords){
        this.favoriteRecords=favoriteRecords;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_record_preview, parent, false);
        return new FavoriteRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecordViewHolder holder, int position) {
        FavoriteRecord record = favoriteRecords.get(position);

        if (record != null) {
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();

            if ("HERO".equals(record.getType())) {
                layoutParams.height = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 300, holder.itemView.getResources().getDisplayMetrics());
            } else {
                layoutParams.height = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 124, holder.itemView.getResources().getDisplayMetrics());
            }

            holder.cardView.setLayoutParams(layoutParams);

            // Обрезка info до 20 символов
            String info = record.getInfo();
            if (info != null && info.length() > 20) {
                info = info.substring(0, 20) + "...";
            }
            holder.tvInfo.setText(info != null ? info : "");

            holder.tvName.setText(record.getName());

            if (record.getAvatarUrl() != null && !record.getAvatarUrl().isEmpty()) {
                Glide.with(context)
                        .load(record.getAvatarUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_empty_photo)
                        .into(holder.iv_image);
            }
        }
    }


    @Override
    public int getItemCount() {
        return favoriteRecords.size();
    }

    static class FavoriteRecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, tvInfo;
        private final ImageView iv_image;
        private final CardView cardView;

        public FavoriteRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            iv_image=itemView.findViewById(R.id.iv_preview_image);
            cardView=itemView.findViewById(R.id.cv_top);
        }
    }
}
