package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentNewsBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.NewsAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    private RecyclerView rv_news_container;
    private RelativeLayout rl_top_element;

    private NewsFragmentViewModel vm;
    @Inject
    NewsFragmentViewModelFactory vmFactory;

    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentNewsBinding.inflate(inflater, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectNewsFragment(this);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(NewsFragmentViewModel.class);

        initialization();

        setObserver();

        setOnItemClicker();

        addOnScrollListener();

        return binding.getRoot();
    }

    private void initialization() {

        rv_news_container=binding.rlNewsContainer;
        rl_top_element=binding.rlTopElement;

        setAdapter();
    }

    private void setAdapter(){
        List<NewsPreviewItem> news = new ArrayList<>();
        adapter = new NewsAdapter(news,getContext(),getParentFragmentManager());

        binding.rlNewsContainer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rlNewsContainer.setAdapter(adapter);
    }

    private void setObserver() {
        vm.getHeroesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<NewsPreviewItem>>() {
            @Override
            public void onChanged(List<NewsPreviewItem> newsPreviewItems) {
                adapter.setNewNews(newsPreviewItems);
            }
        });

        vm.getVisibilityTopElementMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) fadeInTopElement();
                else fadeOutTopElement();
            }
        });
    }



    private void addOnScrollListener() {
        binding.rlNewsContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() > 0) {
                    vm.setVisibilityTopElement(false);
                }
                else {
                    vm.setVisibilityTopElement(true);
                }
            }
        });
    }

    private void fadeOutTopElement() {
        if (rl_top_element.getVisibility() == View.VISIBLE) {
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(rl_top_element, "alpha", 1f, 0f);
            fadeOut.setDuration(300);
            fadeOut.start();
            rl_top_element.setVisibility(View.GONE);
        }
    }

    private void fadeInTopElement() {
        if (rl_top_element.getVisibility() == View.GONE) {
            rl_top_element.setVisibility(View.VISIBLE);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(rl_top_element, "alpha", 0f, 1f);
            fadeIn.setDuration(300);
            fadeIn.start();
        }
    }


    private void setOnItemClicker() {

    }
}