package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.FavoriteRecord;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowEventDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowHeroDialogFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecordsAdapter extends RecyclerView.Adapter<FavoriteRecordsAdapter.FavoriteRecordViewHolder> {

    private List<FavoriteRecord> favoriteRecords=new ArrayList<>();
    private final Context context;
    private final FragmentManager fragmentManager;

    public FavoriteRecordsAdapter(Context context, FragmentManager fragmentManager) {
        this.context=context;
        this.fragmentManager=fragmentManager;
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

            if ("HERO".equals(record.getType())) {
                holder.tv_show_full_record.setOnClickListener(v->{
                    ShowHeroDialogFragment dialogFragment = new ShowHeroDialogFragment(record.getId());
                    dialogFragment.show(fragmentManager, "event_dialog");
                    fragmentManager.executePendingTransactions();
                });
            }
            else {
                holder.tv_show_full_record.setOnClickListener(v->{
                    ShowEventDialogFragment dialogFragment = new ShowEventDialogFragment(record.getId());
                    dialogFragment.show(fragmentManager, "event_dialog");
                    fragmentManager.executePendingTransactions();
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return favoriteRecords.size();
    }

    static class FavoriteRecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, tvInfo,tv_show_full_record;
        private final ImageView iv_image;
        private final CardView cardView;

        public FavoriteRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            iv_image=itemView.findViewById(R.id.iv_preview_image);
            cardView=itemView.findViewById(R.id.cv_top);
            tv_show_full_record=itemView.findViewById(R.id.tv_show_full_record);
        }
    }
}
