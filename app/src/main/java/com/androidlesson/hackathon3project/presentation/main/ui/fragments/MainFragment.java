package com.androidlesson.hackathon3project.presentation.main.ui.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentMainBinding;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    private MainFragmentViewModel vm;
    @Inject
    MainFragmentViewModelFactory vmFactory;

    private BottomNavigationView bottomNavigationView;

    private Fragment currFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentMainBinding.inflate(inflater, container, false);

        initialization();

        setObserver();

        setOnItemClicker();

        return binding.getRoot();
    }

    private void initialization() {
        ((App) requireActivity().getApplication()).appComponent.injectMainFragment(this);

        vm = new ViewModelProvider(this,vmFactory).get(MainFragmentViewModel.class);

        currFragment=vm.getFragmentContainerLiveData().getValue();
        if (currFragment!=null) getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();


        bottomNavigationView=binding.bnvMainBottomBar;
        bottomNavigationView.setItemIconTintList(null);
    }

    private void setObserver() {
        vm.getFragmentContainerLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment!=null && (currFragment==null || currFragment!=fragment)) {
                    currFragment=fragment;
                    getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();
                }
            }
        });
    }

    private void setOnItemClicker() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            vm.replaceFragment(item.getItemId());

            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
                View view = bottomNavigationView.findViewById(menuItem.getItemId());

                if (view != null && view instanceof ViewGroup) {
                    ViewGroup itemView = (ViewGroup) view;
                    View iconView = itemView.findViewById(com.google.android.material.R.id.icon);

                    if (iconView != null) {
                        TextView label = itemView.findViewWithTag("menu_label");
                        if (label == null) {
                            label = new TextView(getContext());
                            label.setTag("menu_label");
                            label.setTextColor(Color.BLACK);
                            label.setTextSize(14);
                            label.setTypeface(Typeface.DEFAULT_BOLD);
                            label.setVisibility(View.INVISIBLE);
                            itemView.addView(label);
                        }

                        // Показать текст рядом с иконкой
                        if (item.getItemId() == menuItem.getItemId()) {
                            label.setText(menuItem.getTitle());
                            label.setVisibility(View.VISIBLE);
                        } else {
                            label.setVisibility(View.INVISIBLE);
                        }

                        itemView.setBackgroundResource(R.drawable.bg_selected_item);

                        View backgroundView = itemView.findViewById(android.R.id.background);
                        if (backgroundView != null) {
                            ViewGroup.LayoutParams params = backgroundView.getLayoutParams();
                            params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                            if (i < bottomNavigationView.getMenu().size() - 1) {
                                View nextView = bottomNavigationView.findViewById(bottomNavigationView.getMenu().getItem(i + 1).getItemId());
                                params.width = nextView.getLeft() - view.getRight();
                            } else {
                                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            }

                            backgroundView.setLayoutParams(params);
                        }
                    }
                }
            }
            return true;
        });
    }
}