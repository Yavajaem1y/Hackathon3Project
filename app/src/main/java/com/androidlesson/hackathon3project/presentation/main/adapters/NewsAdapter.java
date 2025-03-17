package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.models.TopRoundCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<NewsPreviewItem> news=new ArrayList<>();
    private Context context;

    public NewsAdapter(List<NewsPreviewItem> news, Context context) {
        Log.d("NewAdapter","Size "+news.size());
        this.news = news;

        sortArray();
        this.context = context;
    }

    private void sortArray(){
        news.sort((item1, item2) -> {
            long num1 = Long.parseLong(item1.getId().substring(0, 14));
            long num2 = Long.parseLong(item2.getId().substring(0, 14));
            return Long.compare(num2, num1);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewNews(List<NewsPreviewItem> news){
        for (NewsPreviewItem i : news){
            if (!this.news.contains(i)){
                this.news.add(i);
            }
        }
        sortArray();
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
        NewsPreviewItem item=news.get(position);

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

        if(item.getNewsType()=="HERO") {
            holder.iv_type.setText("Герой");
        }
        else holder.iv_type.setText("Событие");

    }

    @Override
    public int getItemCount() {
        return news.size();
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
