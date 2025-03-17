package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentNewsBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.HeroPreviewAdapter;
import com.androidlesson.hackathon3project.presentation.main.adapters.NewsAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    private RecyclerView rl_news_container;

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

        return binding.getRoot();
    }

    private void initialization() {

        rl_news_container=binding.rlNewsContainer;

        setAdapter();
    }

    private void setAdapter(){
        List<NewsPreviewItem> news = new ArrayList<>();
        adapter = new NewsAdapter(news,getContext());

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
    }

    private void setOnItemClicker() {

    }
}