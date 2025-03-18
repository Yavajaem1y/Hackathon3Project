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

    private List<HeroData> heroList;
    private List<String> ids=new ArrayList<>();
    private FragmentManager fragmentManager;

    public HeroPreviewAdapter(List<HeroData> heroList, FragmentManager fragmentManager) {
        this.heroList = new ArrayList<>(heroList);
        this.fragmentManager = fragmentManager;
    }

    public HeroPreviewAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.heroList=new ArrayList<>();
    }

    public void setNewPreviews(List<HeroItemPreview> previews){
        for (HeroItemPreview i:previews){
            HeroData hero=new HeroData(i.getName(),i.getAvatar(),i.getId());
            if (!this.ids.contains(hero.getId())){
                this.heroList.add(hero);
                ids.add(hero.getId());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        HeroData hero = heroList.get(position);
        return hero == null ? R.layout.item_add_new_hero : R.layout.item_hero_preview;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        HeroData hero = heroList.get(position);

        if (hero != null) {
            holder.heroName.setText(hero.getHeroName());
            Glide.with(holder.itemView.getContext()).load(hero.getHeroAvatarImage()).into(holder.heroAvatar);
        }

        holder.itemView.setOnClickListener(v -> {
            if (hero == null) {
                addNewHero(holder.itemView.getContext());
            } else {
                ShowHeroDialogFragment dialogFragment = new ShowHeroDialogFragment(hero.getId());
                dialogFragment.show(fragmentManager, "my_dialog");
            }
        });
    }

    private void addNewHero(Context context) {
        AddHeroDialogFragment dialogFragment = new AddHeroDialogFragment();
        dialogFragment.show(fragmentManager, "my_dialog");
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public static class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView heroName;
        ImageView heroAvatar;

        @SuppressLint("ResourceType")
        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            heroName = itemView.findViewById(R.id.tv_name_and_surname);
            heroAvatar = itemView.findViewById(R.id.iv_hero_image);
        }
    }

    public static class AddHeroViewHolder extends RecyclerView.ViewHolder {
        public AddHeroViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
