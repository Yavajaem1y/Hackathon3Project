package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.domain.main.models.MapPoint;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.PointDetailsActivity;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.TestActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class MapPointsAdapter extends RecyclerView.Adapter<MapPointsAdapter.PointViewHolder>{
    private final Context context;
    private final List<MapPoint> pointList;
    private UserData userData;
    private int moduleSize;

    public MapPointsAdapter(Context context, List<MapPoint> pointList, UserData userData, int moduleSize) {
        this.context = context;
        this.pointList = pointList;
        this.userData = userData;
        this.moduleSize=moduleSize;

        Log.d("MapPoint", String.valueOf(userData==null));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUserData(UserData newUserData) {
        this.userData = newUserData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map_point, parent, false);
        return new PointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        MapPoint point = pointList.get(position);
        if (userData != null) {
            int currentId = userData.getCurrentPoint();

            holder.tv_point_info.setText(point.getInfo());


            if (Objects.equals(point.getType(), "ARTICLE")){
                if (point.getId() <= currentId) {
                    holder.imageView.setImageResource(R.drawable.ic_point_article);
                }
                else
                    holder.imageView.setImageResource(R.drawable.ic_lock_point_article);
            }
            else if (Objects.equals(point.getType(), "HERO")){
                if (point.getId() <= currentId) {
                    holder.imageView.setImageResource(R.drawable.ic_point_hero);
                }
                else
                    holder.imageView.setImageResource(R.drawable.ic_lock_point_hero);
            }
            else if (Objects.equals(point.getType(), "WEAPON")){
                if (point.getId() <= currentId) {
                    holder.imageView.setImageResource(R.drawable.ic_point_weapon);
                }
                else
                    holder.imageView.setImageResource(R.drawable.ic_lock_point_weapon);
            }
            else{
                if (point.getId() <= currentId) {
                    holder.imageView.setImageResource(R.drawable.ic_point_test);
                }
                else
                    holder.imageView.setImageResource(R.drawable.ic_lock_point_weapon);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (userData != null && point.getId() <= userData.getCurrentPoint()) {
                if (!Objects.equals(point.getType(), "TEST")) {
                    Intent intent = new Intent(context, PointDetailsActivity.class);
                    intent.putExtra("POINT_NAME", point.getName());
                    intent.putExtra("USER_ID", userData.getUserId());
                    intent.putExtra("USER_LAST_POINT", userData.getCurrentPoint());
                    intent.putExtra("POINT_ITEMS", (Serializable) point.getItems());
                    intent.putExtra("POINT_ID", point.getId());
                    intent.putExtra("POINT_AVATAR", point.getAvatar());
                    intent.putExtra("POINT_HINT", point.getHint());
                    intent.putExtra("MODULE_SIZE", moduleSize);
                    intent.putExtra("USER_POINTS_COMPLETED", userData.getPointsCompleted());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra("POINT_QUESTIONS", (Serializable) point.getQuestions());
                    intent.putExtra("USER_ID", userData.getUserId());
                    intent.putExtra("USER_LAST_POINT", userData.getCurrentPoint());
                    intent.putExtra("POINT_ID", point.getId());
                    intent.putExtra("MODULE_SIZE", moduleSize);
                    intent.putExtra("USER_POINTS_COMPLETED", userData.getPointsCompleted());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public static class PointViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_point_info;

        public PointViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_point_icon);
            tv_point_info=itemView.findViewById(R.id.tv_point_info);
        }
    }
}
