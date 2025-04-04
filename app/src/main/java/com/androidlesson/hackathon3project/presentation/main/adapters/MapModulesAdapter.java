package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;

import java.util.ArrayList;
import java.util.List;

public class MapModulesAdapter extends RecyclerView.Adapter<MapModulesAdapter.ModuleViewHolder>{
    private List<MapModule> moduleList = new ArrayList<>();
    private UserData currentUserData;

    public MapModulesAdapter() {
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewModulesList(List<MapModule> moduleList) {
        this.moduleList = moduleList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentUserData(UserData userData) {
        this.currentUserData = userData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_map_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        MapModule module = moduleList.get(position);
        holder.tvModuleName.setText(module.name);

        MapPointsAdapter pointAdapter = new MapPointsAdapter(
                holder.itemView.getContext(),
                module.points,
                currentUserData,
                module.getPoints().size()
        );
        holder.rvPointsHolder.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvPointsHolder.setAdapter(pointAdapter);
        holder.pointsAdapter = pointAdapter;
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView tvModuleName;
        RecyclerView rvPointsHolder;
        MapPointsAdapter pointsAdapter;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModuleName = itemView.findViewById(R.id.tv_module_name);
            rvPointsHolder = itemView.findViewById(R.id.rv_points_holder);
        }
    }
}
