package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.domain.main.models.MapModule;
import com.androidlesson.domain.main.models.MapPoint;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentMainBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.MapModulesAdapter;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.OnboardingActivity;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.TestActivity;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mapFragmentViewModel.MapFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mapFragmentViewModel.MapFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MapFragment extends Fragment {

    private RecyclerView rvModulesHolder;
    private MapModulesAdapter moduleAdapter;

    private View view;

    private MapFragmentViewModel vm;
    @Inject
    MapFragmentViewModelFactory vmFactory;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_map, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectMapFragment(this);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(MapFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

        initialization();

        observer();

        return view;
    }

    private void initialization(){
        rvModulesHolder = view.findViewById(R.id.rv_modules_holder);
        rvModulesHolder.setLayoutManager(new LinearLayoutManager(getContext()));

        moduleAdapter = new MapModulesAdapter();
        rvModulesHolder.setAdapter(moduleAdapter);
    }

    private void observer() {
        vm.getMapModuleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<MapModule>>() {
            @Override
            public void onChanged(List<MapModule> modules) {
                if (modules != null) {
                    sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
                        @Override
                        public void onChanged(UserData userData) {
                            if (userData != null) {
                                moduleAdapter = new MapModulesAdapter();
                                moduleAdapter.setCurrentUserData(userData);
                                moduleAdapter.setNewModulesList(modules);
                                rvModulesHolder.setAdapter(moduleAdapter);
                            }
                        }
                    });
                }
            }
        });

        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData != null) {
                    if (userData.getIsFirstTime() == 1) {
                        vm.setCurrentUserData(userData);
                        vm.setIsFirstTime();
                        startActivity(new Intent(getActivity(), OnboardingActivity.class));
                    }
                }
            }
        });
    }
}