package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentUserProfileBinding;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.DotsMenuFragmentFromCurrnetUserActivity;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;


public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private ImageView ic_dots_menu;
    private TextView tv_user_name_and_surname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentUserProfileBinding.inflate(inflater, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectUserProfileFragment(this);

        sharedViewModel = new ViewModelProvider(requireActivity(), sharedViewModelFactory).get(SharedViewModel.class);

        initialization();

        setObserver();

        setOnItemClicker();

        return binding.getRoot();
    }

    private void initialization() {
        ic_dots_menu=binding.icDotsMenu;
        tv_user_name_and_surname=binding.tvCurrUserNameAndSurname;
        UserData userData = null;
        
        if (sharedViewModel.getCurrentUserDataLiveData().getValue()!=null) {
            userData = sharedViewModel.getCurrentUserDataLiveData().getValue();
            if (userData.getUserName()!=null && userData.getUserSurname()!=null) tv_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
        }
    }

    private void setObserver() {
        sharedViewModel.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    tv_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
                }
            }
        });
    }

    private void setOnItemClicker() {
        ic_dots_menu.setOnClickListener(v->{
            FragmentManager fragmentManager = getParentFragmentManager();
            DotsMenuFragmentFromCurrnetUserActivity dialogFragment = new DotsMenuFragmentFromCurrnetUserActivity();
            dialogFragment.show(fragmentManager, "my_dialog");
        });
    }
}