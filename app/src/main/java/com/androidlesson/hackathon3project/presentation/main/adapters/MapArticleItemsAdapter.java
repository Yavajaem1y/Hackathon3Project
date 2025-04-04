package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.hackathon3project.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MapArticleItemsAdapter extends RecyclerView.Adapter<MapArticleItemsAdapter.ItemViewHolder>{

    private final List<MapArticleItem> itemList;
    private final Context context;

    public MapArticleItemsAdapter(Context context, List<MapArticleItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map_article, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MapArticleItem item = itemList.get(position);

        if (item.getText() != null && !item.getText().isEmpty()) {
            holder.textView.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);

            String text = item.getText()
                    .replaceAll("<title>", "<h3 style='text-align:center;'>")
                    .replaceAll("</title>", "</h3>");


            holder.textView.setText(Html.fromHtml(text));
        }
        else if (item.getImage() != null && !item.getImage().isEmpty()) {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.textView.setVisibility(View.GONE);

            Glide.with(context).load(item.getImage()).centerCrop().into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_text);
            imageView = itemView.findViewById(R.id.iv_item_image);
            cardView=itemView.findViewById(R.id.cv_image_holder);
        }
    }
}
