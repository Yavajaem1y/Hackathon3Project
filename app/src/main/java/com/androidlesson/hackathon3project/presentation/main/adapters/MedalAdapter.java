package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.models.MedalsHolder;

import java.util.ArrayList;
import java.util.List;

public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.MedalViewHolder> {
    private Context context;
    private List<Medal> medals=new ArrayList<>();
    List<Medal> allMedals=new MedalsHolder().getMedals();

    public MedalAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMedals(List<Integer> unlockMedalsId){
        List<Medal> newMedals=new ArrayList<>();
        for (int i=0;i<allMedals.size();i++){
            if (!unlockMedalsId.contains(i)){
                Medal lockMedal= allMedals.get(i);
                lockMedal.setMedal_image(R.drawable.ic_medal_lock);
                newMedals.add(lockMedal);
            }
            else {
                newMedals.add(allMedals.get(i));
            }
        }
        this.medals=newMedals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medal, parent, false);
        return new MedalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedalViewHolder holder, int position) {
        Medal medal = medals.get(position);
        holder.tvMedalName.setText(medal.getName());
        holder.tvMedalInfo.setText(medal.getInfo_preview());
        holder.ivMedal.setImageResource(medal.getMedal_image());
    }

    @Override
    public int getItemCount() {
        return medals.size();
    }

    public static class MedalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMedal;
        TextView tvMedalName;
        TextView tvMedalInfo;

        public MedalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMedal = itemView.findViewById(R.id.iv_medal);
            tvMedalName = itemView.findViewById(R.id.tv_medal_name);
            tvMedalInfo = itemView.findViewById(R.id.tv_medal_info);
        }
    }
}
