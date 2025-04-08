package com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentUserProfileBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.MedalPreviewAdapter;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AllUserHeroesDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.DotsMenuFragmentFromCurrnetUserActivity;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;


import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private MedalPreviewAdapter adapter;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private ImageView ic_dots_menu;
    private RelativeLayout b_my_heroes;
    private TextView tv_user_name_and_surname, tv_user_id,tv_count_points_completed,tv_tv_count_points_completed,tv_count_modules_completed,tv_tv_count_modules_completed,tv_count_tests_completed,tv_tv_count_tests_completed;
    private CircleImageView civ_user_avatar;
    private RecyclerView rv_medals;

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
        tv_user_id=binding.tvLogin;
        civ_user_avatar=binding.civCurrUserAvatar;
        b_my_heroes=binding.bMyHeroes;
        tv_count_tests_completed=binding.tvCountTestsCompleted;
        tv_count_modules_completed=binding.tvCountModulesCompleted;
        tv_count_points_completed=binding.tvCountPointsCompleted;
        tv_tv_count_tests_completed=binding.tvTvCountTestsCompleted;
        tv_tv_count_modules_completed=binding.tvTvCountModulesCompleted;
        tv_tv_count_points_completed=binding.tvTvCountPointsCompleted;

        UserData userData = null;
        
        if (sharedViewModel.getCurrentUserDataLiveData().getValue()!=null) {
            userData = sharedViewModel.getCurrentUserDataLiveData().getValue();
            if (userData.getUserName()!=null && userData.getUserSurname()!=null) tv_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
            if (userData.getUserId()!=null && !userData.getUserId().isEmpty()) tv_user_id.setText("@"+userData.getUserId());
        }

        setAdapter();
    }

    private void setAdapter(){
        adapter = new MedalPreviewAdapter(getContext());
        binding.rvMedalHoler.setAdapter(adapter);
        binding.rvMedalHoler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setObserver() {
        sharedViewModel.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    tv_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
                    tv_user_id.setText("@"+userData.getUserId());
                    if (userData.getImageData()!=null && !userData.getImageData().isEmpty()){
                        Glide.with(getContext()).load(userData.getImageData()).into(civ_user_avatar);
                    }
                    tv_count_points_completed.setText(String.valueOf(userData.getPointsCompleted()-userData.getTestsCompleted()));
                    tv_count_modules_completed.setText(String.valueOf(userData.getCurrentPoint()/10));
                    tv_count_tests_completed.setText(String.valueOf(userData.getTestsCompleted()));
                    if (userData.getPointsCompleted()-userData.getTestsCompleted()%10==1 && userData.getPointsCompleted()-userData.getTestsCompleted()!=11){
                        tv_tv_count_points_completed.setText("Статья\nпрочитана");
                    }
                    else {
                        tv_tv_count_points_completed.setText("Статей\nпрочитано");
                    }
                    if ((userData.getCurrentPoint()/10)%10==1 && (userData.getCurrentPoint()/10)!=11){
                        tv_tv_count_modules_completed.setText("Раздел\nпройден");
                    }
                    else {
                        tv_tv_count_modules_completed.setText("Разделов\nпройдено");
                    }
                    if (userData.getTestsCompleted()%10==1 && userData.getTestsCompleted()!=11){
                        tv_tv_count_tests_completed.setText("Тест\nпройден");
                    }
                    else {
                        tv_tv_count_tests_completed.setText("Тестов\nпройдено");
                    }

                    if (userData.getMedalsId()!=null){
                        adapter.setNewMedalList(userData.getMedalsId());
                    }
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

        b_my_heroes.setOnClickListener(v->{
            FragmentManager fragmentManager = getParentFragmentManager();
            AllUserHeroesDialogFragment dialogFragment = new AllUserHeroesDialogFragment();
            dialogFragment.show(fragmentManager, "my_dialog");
        });
    }
}