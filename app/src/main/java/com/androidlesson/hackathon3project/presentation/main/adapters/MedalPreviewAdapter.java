package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.models.MedalsHolder;

import java.util.ArrayList;
import java.util.List;

public class MedalPreviewAdapter extends RecyclerView.Adapter<MedalPreviewAdapter.MedalPreviewViewHolder> {

    private List<Medal> medalList=new ArrayList<>();
    private Context context;

    public  MedalPreviewAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewMedalList(List<Integer> medalsList){
        List<Medal> newMedalList=new ArrayList<>();
        for (int i:medalsList){
            newMedalList.add(new MedalsHolder().getMedalById(i));
        }
        this.medalList=newMedalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedalPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medal_preview, parent, false);
        return new MedalPreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedalPreviewViewHolder holder, int position) {
        Medal medal = medalList.get(position);
        holder.ivMedal.setImageResource(medal.getMedal_image());
    }

    @Override
    public int getItemCount() {
        return medalList.size();
    }

    public static class MedalPreviewViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMedal;

        public MedalPreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMedal = itemView.findViewById(R.id.iv_medal);
        }
    }
}
