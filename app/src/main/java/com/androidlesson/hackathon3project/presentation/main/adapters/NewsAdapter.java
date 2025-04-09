package com.androidlesson.hackathon3project.presentation.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.NewsEventPreviewItem;
import com.androidlesson.domain.main.models.NewsHeroPreviewItem;
import com.androidlesson.domain.main.models.NewsItem;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.interfaces.AdapterElementsSize;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnProudClickListener;
import com.androidlesson.hackathon3project.presentation.main.interfaces.VisibilityTopElement;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowEventDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.ShowHeroDialogFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<NewsItem> listNews=new ArrayList<>();
    List<NewsItem> listCurrentNews=new ArrayList<>();
    List<NewsItem> listSearchNews=new ArrayList<>();

    private final AdapterElementsSize adapterElementsSize;
    private final FragmentManager fragmentManager;

    private UserData currentUserData;
    private final OnProudClickListener proudClickListener;
    private String searchFilter="";
    private String lastEventId="";
    private final VisibilityTopElement visibilityTopElement;

    public NewsAdapter(AdapterElementsSize adapterElementsSize,FragmentManager fragmentManager, UserData userData, OnProudClickListener proudClickListener,VisibilityTopElement visibilityTopElement) {
        this.adapterElementsSize=adapterElementsSize;
        this.fragmentManager=fragmentManager;
        currentUserData=userData;
        this.visibilityTopElement=visibilityTopElement;
        this.proudClickListener=proudClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUserData(UserData userData){
        currentUserData=userData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (listCurrentNews.isEmpty()) {
            return -1;
        }
        return listCurrentNews.get(position).getType();
    }


    public void setNewsList(List<NewsItem> listNews, int filter){
        this.listNews=listNews;

        int defaultFilter = (filter == 0) ? 1 : filter;

        filterItems(defaultFilter!=1);
    }

    public void filterItems(boolean showHeroes) {
        listCurrentNews.clear();

        List<NewsItem> filteredList;

        if (showHeroes) {
            filteredList = listNews.stream()
                    .filter(item -> item instanceof NewsHeroPreviewItem)
                    .sorted((item1, item2) -> Long.compare(
                            extractId(item2.getNewsId()),
                            extractId(item1.getNewsId())
                    ))
                    .collect(Collectors.toList());
        } else {
            filteredList = listNews.stream()
                    .filter(item -> item instanceof NewsEventPreviewItem)
                    .sorted((item1, item2) -> Long.compare(
                            extractId(item2.getNewsId()),
                            extractId(item1.getNewsId())
                    ))
                    .collect(Collectors.toList());
        }

        listCurrentNews.addAll(filteredList);

        search(searchFilter,showHeroes? 2 : 1);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void search(String s, int filter) {
        searchFilter=s;
        if (s.isEmpty()) {
            listSearchNews = listCurrentNews;
        } else {
            listSearchNews = new ArrayList<>();
            if (filter == 1) {
                listSearchNews = listCurrentNews.stream()
                        .filter(item -> item instanceof NewsEventPreviewItem)
                        .map(item -> (NewsEventPreviewItem) item)
                        .filter(item -> item.getName().startsWith(s))
                        .collect(Collectors.toList());
            }
            else {
                listSearchNews = listCurrentNews.stream()
                        .filter(item -> item instanceof NewsHeroPreviewItem)
                        .map(item -> (NewsHeroPreviewItem) item)
                        .filter(item -> item.getName().startsWith(s))
                        .collect(Collectors.toList());
            }
        }

        if (!listSearchNews.isEmpty() && listSearchNews.get(0) instanceof NewsEventPreviewItem) {
            lastEventId = ((NewsEventPreviewItem) listSearchNews.get(0)).getId();
        }

        notifyDataSetChanged();
    }


    private long extractId(String id) {
        try {
            return Long.parseLong(id.substring(0, 14));
        } catch (Exception e) {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == NewsItem.TYPE_HERO) {
            View view = inflater.inflate(R.layout.item_news_hero_preview, parent, false);
            int screenWidth = parent.getContext().getResources().getDisplayMetrics().widthPixels;
            int marginPx = dpToPx(parent.getContext(), 24);
            int marginBottomPx = dpToPx(parent.getContext(), 8);
            int itemWidth = (screenWidth / 2) - marginPx;

            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, marginPx, marginBottomPx);
            view.setLayoutParams(layoutParams);

            return new HeroViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_news_event_preview, parent, false);
            return new EventViewHolder(view);
        }
    }

    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsItem item = listSearchNews.get(position);

        if (item instanceof NewsHeroPreviewItem) {
            NewsHeroPreviewItem hero = (NewsHeroPreviewItem) item;
            if (holder instanceof HeroViewHolder) {
                ((HeroViewHolder) holder).name.setText(hero.getName());
                ((HeroViewHolder) holder).date.setText(hero.getDate());
                if (hero.getAvatar() != null && !hero.getAvatar().isEmpty()) {
                    Glide.with(holder.itemView.getContext()).load(hero.getAvatar()).centerCrop().into(((HeroViewHolder) holder).avatar);
                } else {
                    Glide.with(holder.itemView.getContext()).load("dasda").centerCrop().into(((HeroViewHolder) holder).avatar);
                }
                if (currentUserData.getListFavoriteRecordIds().contains(hero.getId())) {
                    Glide.with(holder.itemView.getContext()).load(R.drawable.ic_red_heart).into(((HeroViewHolder) holder).proud);
                } else {
                    Glide.with(holder.itemView.getContext()).load(R.drawable.ic_white_heart).into(((HeroViewHolder) holder).proud);
                }
            }

            holder.itemView.setOnClickListener(v -> {
                if (holder instanceof HeroViewHolder) {
                    assert item instanceof NewsHeroPreviewItem;
                    NewsHeroPreviewItem heroItem = (NewsHeroPreviewItem) item;

                    DialogFragment existingDialog = (DialogFragment) fragmentManager.findFragmentByTag("hero_dialog");
                    if (existingDialog != null && existingDialog.isVisible()) {
                        existingDialog.dismissAllowingStateLoss();
                    }

                    new Handler().post(() -> {
                        ShowHeroDialogFragment dialogFragment = new ShowHeroDialogFragment(heroItem.getId(), visibilityTopElement);
                        dialogFragment.show(fragmentManager, "hero_dialog");
                        fragmentManager.executePendingTransactions();
                        if (dialogFragment.getDialog() != null) {
                            dialogFragment.getDialog().setOnDismissListener(dialog -> visibilityTopElement.getVisibility(true));
                        }
                    });
                }

                visibilityTopElement.getVisibility(false);
            });

            // Обработка кнопки "proud" для героя
            HeroViewHolder heroViewHolder = (HeroViewHolder) holder;
            heroViewHolder.proud.setOnClickListener(proud -> {
                assert item instanceof NewsHeroPreviewItem;
                NewsHeroPreviewItem heroItem = (NewsHeroPreviewItem) item;

                boolean isFavorite = currentUserData.getListFavoriteRecordIds().contains(heroItem.getId());

                if (isFavorite) {
                    currentUserData.getListFavoriteRecordIds().remove(heroItem.getId());
                } else {
                    currentUserData.getListFavoriteRecordIds().add(heroItem.getId());
                }

                // Отложенный вызов, чтобы гарантировать обновление UI после изменения данных
                new Handler().post(() -> {
                    // Обновляем item сразу
                    notifyItemChanged(position);

                    // Отложенный вызов метода proudClickListener.onProudHeroClick
                    if (proudClickListener != null) {
                        Log.d("Proud", "proud on hero");
                        proudClickListener.onProudHeroClick(hero.getId());
                    }
                });
            });

        } else if (item instanceof NewsEventPreviewItem) {
            NewsEventPreviewItem eventItem = (NewsEventPreviewItem) item;
            if (holder instanceof EventViewHolder) {
                ((EventViewHolder) holder).name.setText(eventItem.getName());
                ((EventViewHolder) holder).date.setText(eventItem.getDate());
                ((EventViewHolder) holder).info.setText(eventItem.getInfo());
                if (eventItem.getAvatar() != null && !eventItem.getAvatar().isEmpty()) {
                    Glide.with(holder.itemView.getContext()).load(eventItem.getAvatar()).centerCrop().into(((EventViewHolder) holder).avatar);
                } else {
                    Glide.with(holder.itemView.getContext()).load("dasda").centerCrop().into(((EventViewHolder) holder).avatar);
                }
                if (currentUserData != null && currentUserData.getListFavoriteRecordIds() != null) {
                    if (currentUserData.getListFavoriteRecordIds().contains(eventItem.getId())) {
                        Glide.with(holder.itemView.getContext()).load(R.drawable.ic_red_heart).into(((EventViewHolder) holder).proud);
                    } else {
                        Glide.with(holder.itemView.getContext()).load(R.drawable.ic_white_heart).into(((EventViewHolder) holder).proud);
                    }
                }

                if (!lastEventId.isEmpty()) {
                    if (eventItem.getId().equals(lastEventId)) {
                        ((EventViewHolder) holder).ll_article_of_the_day.setVisibility(View.VISIBLE);
                    } else {
                        ((EventViewHolder) holder).ll_article_of_the_day.setVisibility(View.GONE);
                    }
                }
            }

            holder.itemView.setOnClickListener(v -> {
                if (holder instanceof EventViewHolder) {
                    assert item instanceof NewsEventPreviewItem;
                    NewsEventPreviewItem event = (NewsEventPreviewItem) item;

                    DialogFragment existingDialog = (DialogFragment) fragmentManager.findFragmentByTag("event_dialog");
                    if (existingDialog != null && existingDialog.isVisible()) {
                        existingDialog.dismissAllowingStateLoss();
                    }

                    new Handler().post(() -> {
                        ShowEventDialogFragment dialogFragment = new ShowEventDialogFragment(event.getNewsId(), visibilityTopElement, getLastEventId());
                        dialogFragment.show(fragmentManager, "event_dialog");
                        fragmentManager.executePendingTransactions();
                        if (dialogFragment.getDialog() != null) {
                            dialogFragment.getDialog().setOnDismissListener(dialog -> visibilityTopElement.getVisibility(true));
                        }
                    });
                }

                visibilityTopElement.getVisibility(false);
            });

            // Обработка кнопки "proud" для события
            EventViewHolder eventViewHolder = (EventViewHolder) holder;
            eventViewHolder.proud.setOnClickListener(proud -> {
                assert item instanceof NewsEventPreviewItem;
                NewsEventPreviewItem event = (NewsEventPreviewItem) item;

                boolean isFavorite = currentUserData.getListFavoriteRecordIds().contains(event.getId());

                if (isFavorite) {
                    currentUserData.getListFavoriteRecordIds().remove(event.getId());
                } else {
                    currentUserData.getListFavoriteRecordIds().add(event.getId());
                }

                // Отложенный вызов, чтобы гарантировать обновление UI после изменения данных
                new Handler().post(() -> {
                    // Обновляем item сразу
                    notifyItemChanged(position);

                    // Отложенный вызов метода proudClickListener.onProudEventClick
                    if (proudClickListener != null) {
                        Log.d("Proud", "proud on event");
                        proudClickListener.onProudEventClick(event.getId());
                    }
                });
            });
        }
    }

    public String getLastEventId() {
        return lastEventId;
    }

    @Override
    public int getItemCount() {
        adapterElementsSize.getSize(listSearchNews.size());
        return listSearchNews.size();
    }

    // ViewHolder для NewsHeroPreviewItem
    static class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        ImageView avatar,proud;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            avatar = itemView.findViewById(R.id.iv_preview_image);
            proud= itemView.findViewById(R.id.iv_heart);
        }
    }

    // ViewHolder для NewsEventPreviewItem
    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, info;
        ImageView avatar,proud;
        LinearLayout ll_article_of_the_day;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            info = itemView.findViewById(R.id.tv_info);
            avatar = itemView.findViewById(R.id.iv_preview_image);
            proud= itemView.findViewById(R.id.iv_heart);
            ll_article_of_the_day=itemView.findViewById(R.id.ll_article_of_the_day);
        }
    }
}

