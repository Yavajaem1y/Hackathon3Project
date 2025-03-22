package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.interfaces.AdapterElementsSize;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowHeroDialogFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<NewsPreviewItem> allNews =new ArrayList<>();
    private List<NewsPreviewItem> selectedNews=new ArrayList<>();
    private int filter=1;
    private Context context;
    private FragmentManager fragmentManager;
    private AdapterElementsSize adapterElementsSize;

    public NewsAdapter(List<NewsPreviewItem> allNews, Context context, FragmentManager fragmentManager, AdapterElementsSize adapterElementsSize) {
        Log.d("NewAdapter","Size "+ allNews.size());
        this.allNews = allNews;
        this.selectedNews=allNews;
        filter=1;

        sortArray();
        this.context = context;
        this.adapterElementsSize=adapterElementsSize;
        this.fragmentManager=fragmentManager;
    }

    private void sortArray(){
        allNews.sort((item1, item2) -> {
            long num1 = Long.parseLong(item1.getId().substring(0, 14));
            long num2 = Long.parseLong(item2.getId().substring(0, 14));
            return Long.compare(num2, num1);
        });
    }

    public void setNewNews(List<NewsPreviewItem> news){
        this.allNews=news;
        sortArray();
        setSelectedNews(filter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void search(String input){
        setSelectedNews(filter);
        if (!input.isEmpty()){
            List<NewsPreviewItem> newList=new ArrayList<>();
            for (NewsPreviewItem i:selectedNews){
                if (i.getName().startsWith(input)){
                    newList.add(i);
                }
            }
            selectedNews=newList;
            notifyDataSetChanged();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedNews(int filterType){
        filter=filterType;
        if (filterType==1){
            selectedNews=allNews;
        }
        else if (filterType==2){
            selectedNews=new ArrayList<>();
            for (NewsPreviewItem item:allNews){
                if (Objects.equals(item.getNewsType(), "EVENT")){
                    selectedNews.add(item);
                }
            }
        }
        else {
            selectedNews=new ArrayList<>();
            for (NewsPreviewItem item:allNews){
                if (Objects.equals(item.getNewsType(), "HERO")){
                    selectedNews.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_news_preview;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsPreviewItem item= selectedNews.get(position);

        if (item.getAvatar()!=null && !item.getAvatar().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(item.getAvatar()).centerCrop().into(holder.iv_image);
        }

        holder.tv_name.setText(item.getName());

        if (item.getInfo().length()>24){
            String info=item.getInfo().substring(0, 24)+"...";
            holder.tv_info.setText(info);
        }
        else{
            holder.tv_info.setText(item.getInfo());
        }

        if(Objects.equals(item.getNewsType(), "HERO")) {
            holder.iv_type.setText("Герой");
        }
        else holder.iv_type.setText("Событие");

        holder.itemView.setOnClickListener(v->{
            if (Objects.equals(item.getNewsType(), "HERO")){
                ShowHeroDialogFragment dialogFragment = new ShowHeroDialogFragment(item.getId());
                dialogFragment.show(fragmentManager, "my_dialog");
            }
        });

    }

    @Override
    public int getItemCount() {
        adapterElementsSize.getSize(selectedNews.size());
        return selectedNews.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image;
        TextView tv_name, tv_info,iv_type;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image=itemView.findViewById(R.id.iv_preview_image);
            tv_info=itemView.findViewById(R.id.tv_info);
            tv_name=itemView.findViewById(R.id.tv_name);
            iv_type=itemView.findViewById(R.id.tv_type);
        }
    }
}
