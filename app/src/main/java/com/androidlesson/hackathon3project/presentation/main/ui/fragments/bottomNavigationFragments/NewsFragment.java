package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidlesson.domain.main.models.NewsPreviewItem;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentNewsBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.NewsAdapter;
import com.androidlesson.hackathon3project.presentation.main.interfaces.AdapterElementsSize;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    private RecyclerView rv_news_container;
    private RelativeLayout rl_top_element,rl_bottom_element;
    private TextView tv_filter_all, tv_filter_events, tv_filter_hero,tv_nothing;
    private View left_view,right_view;
    private EditText et_search;

    private NewsFragmentViewModel vm;
    @Inject
    NewsFragmentViewModelFactory vmFactory;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentNewsBinding.inflate(inflater, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectNewsFragment(this);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(NewsFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

        initialization();

        setObserver();

        setOnItemClicker();

        addOnScrollListener();

        addTextChangedListener();

        return binding.getRoot();
    }

    private void initialization() {

        rv_news_container=binding.rlNewsContainer;
        rl_top_element=binding.rlTopElement;
        tv_filter_all=binding.tvFilterAll;
        tv_filter_events=binding.tvFilterEvents;
        tv_filter_hero=binding.tvFilterHero;
        left_view=binding.leftView;
        right_view=binding.rightView;
        et_search=binding.etSearch;
        tv_nothing=binding.tvNothing;
        rl_bottom_element=binding.rlBottomElement;

        setAdapter();
    }

    private void setAdapter(){
        List<NewsPreviewItem> news = new ArrayList<>();
        adapter = new NewsAdapter(news, getContext(), getParentFragmentManager(), new AdapterElementsSize() {
            @Override
            public void getSize(int size) {
                if (size!=0){
                    tv_nothing.setVisibility(View.GONE);
                }
                else tv_nothing.setVisibility(View.VISIBLE);
            }
        });

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

        sharedVM.getRemovedHeroDataIdMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                vm.removeHeroDataById(string);
            }
        });

        vm.getVisibilityTopElementMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) expandTopElement();
                else collapseTopElement();
            }
        });

        vm.getFilterMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==1){
                    tv_filter_all.setTextAppearance(R.style.SelectedFilterStyle);
                    tv_filter_events.setTextAppearance(R.style.UnselectedFilterStyle);
                    tv_filter_hero.setTextAppearance(R.style.UnselectedFilterStyle);

                    tv_filter_all.setBackgroundResource(R.drawable.bg_rounded_white);
                    tv_filter_hero.setBackgroundResource(R.drawable.bg_rounded_null);
                    tv_filter_events.setBackgroundResource(R.drawable.bg_rounded_null);

                    right_view.setVisibility(View.VISIBLE);
                    left_view.setVisibility(View.INVISIBLE);

                    adapter.setSelectedNews(1);
                }
                else if (integer==2){
                    tv_filter_all.setTextAppearance(R.style.UnselectedFilterStyle);
                    tv_filter_events.setTextAppearance(R.style.SelectedFilterStyle);
                    tv_filter_hero.setTextAppearance(R.style.UnselectedFilterStyle);

                    tv_filter_all.setBackgroundResource(R.drawable.bg_rounded_null);
                    tv_filter_hero.setBackgroundResource(R.drawable.bg_rounded_null);
                    tv_filter_events.setBackgroundResource(R.drawable.bg_rounded_white);

                    right_view.setVisibility(View.INVISIBLE);
                    left_view.setVisibility(View.INVISIBLE);

                    adapter.setSelectedNews(2);
                }
                else{
                    tv_filter_all.setTextAppearance(R.style.UnselectedFilterStyle);
                    tv_filter_events.setTextAppearance(R.style.UnselectedFilterStyle);
                    tv_filter_hero.setTextAppearance(R.style.SelectedFilterStyle);

                    tv_filter_all.setBackgroundResource(R.drawable.bg_rounded_null);
                    tv_filter_hero.setBackgroundResource(R.drawable.bg_rounded_white);
                    tv_filter_events.setBackgroundResource(R.drawable.bg_rounded_null);

                    right_view.setVisibility(View.INVISIBLE);
                    left_view.setVisibility(View.VISIBLE);

                    adapter.setSelectedNews(3);
                }
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

    private int originalHeight = -1;

    private void collapseTopElement() {
        if (rl_top_element.getVisibility() == View.VISIBLE) {
            if (originalHeight == -1) {
                originalHeight = rl_top_element.getHeight();
            }

            ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0);
            animator.setDuration(300);
            animator.addUpdateListener(animation -> {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = rl_top_element.getLayoutParams();
                layoutParams.height = animatedValue;
                rl_top_element.setLayoutParams(layoutParams);
            });

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rl_top_element.setVisibility(View.GONE);
                }
            });

            animator.start();
        }
    }

    private void expandTopElement() {
        if (rl_top_element.getVisibility() == View.GONE) {
            rl_top_element.setVisibility(View.VISIBLE);

            if (originalHeight == -1) {
                rl_top_element.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                originalHeight = rl_top_element.getMeasuredHeight();
            }

            ValueAnimator animator = ValueAnimator.ofInt(0, originalHeight);
            animator.setDuration(300);
            animator.addUpdateListener(animation -> {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = rl_top_element.getLayoutParams();
                layoutParams.height = animatedValue;
                rl_top_element.setLayoutParams(layoutParams);
            });

            animator.start();
        }
    }


    private void addTextChangedListener(){
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void setOnItemClicker() {
        tv_filter_all.setOnClickListener(v->{
            vm.setFilter(1);
        });

        tv_filter_events.setOnClickListener(v->{
            vm.setFilter(2);
        });

        tv_filter_hero.setOnClickListener(v->{
            vm.setFilter(3);
        });
    }
}