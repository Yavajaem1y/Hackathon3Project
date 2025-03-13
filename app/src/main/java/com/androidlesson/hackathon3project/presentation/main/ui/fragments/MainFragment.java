package com.androidlesson.hackathon3project.presentation.main.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentMainBinding;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    private MainFragmentViewModel vm;
    @Inject
    MainFragmentViewModelFactory vmFactory;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private ChipNavigationBar bottomNavigationView;

    private Fragment currFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);

        Log.d("MainFragment","Created");

        initialization();

        setObserver();

        setOnItemClicker();

        return binding.getRoot();
    }

    private void initialization() {
        ((App) requireActivity().getApplication()).appComponent.injectMainFragment(this);

        vm = new ViewModelProvider(this, vmFactory).get(MainFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

        sharedVM.getUserEmail();

        currFragment = vm.getFragmentContainerLiveData().getValue();
        if (currFragment != null) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fl_main_fragment_container, currFragment)
                    .commit();
        }

        bottomNavigationView = binding.bnvMainBottomBar;
    }

    private void setObserver() {
        vm.getFragmentContainerLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment != null && (currFragment == null || !currFragment.getClass().equals(fragment.getClass()))) {
                    currFragment = fragment;
                    getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();
                }
            }
        });
    }

    private void setOnItemClicker() {
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                vm.replaceFragment(i);
            }
        });
    }
}