package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.HeroItemPreview;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AddHeroDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowHeroDialogFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HeroPreviewAdapter extends RecyclerView.Adapter<HeroPreviewAdapter.HeroViewHolder> {

    private List<HeroItemPreview> heroList;
    private List<String> ids=new ArrayList<>();
    private FragmentManager fragmentManager;

    public HeroPreviewAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.heroList=new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewPreviews(List<HeroItemPreview> previews){
        this.heroList=previews;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_hero_preview;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new HeroViewHolder(view);
    }

    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        int screenWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;
        int marginPx = dpToPx(holder.itemView.getContext(), 29);
        int itemWidth = (screenWidth / 2) - marginPx;

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = itemWidth;
        holder.itemView.setLayoutParams(layoutParams);

        HeroItemPreview hero = heroList.get(position);

        if (hero != null) {
            holder.heroName.setText(hero.getName());
            if (hero.getAvatar()!=null && !hero.getAvatar().isEmpty())
                Glide.with(holder.itemView.getContext()).load(hero.getAvatar()).centerCrop().into(holder.heroAvatar);

            holder.heroDate.setText(hero.getDate());
        }

        holder.itemView.setOnClickListener(v -> {
                ShowHeroDialogFragment dialogFragment = new ShowHeroDialogFragment(hero.getId(),null);
                dialogFragment.show(fragmentManager, "my_dialog");
        });
    }


    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public static class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView heroName,heroDate;
        ImageView heroAvatar;

        @SuppressLint("ResourceType")
        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            heroName = itemView.findViewById(R.id.tv_name_and_surname);
            heroAvatar = itemView.findViewById(R.id.iv_hero_image);
            heroDate=itemView.findViewById(R.id.tv_date);
        }
    }
}
