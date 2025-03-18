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
import android.widget.TextView;
import android.widget.Toast;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentUserProfileBinding;
import com.androidlesson.hackathon3project.presentation.main.adapters.HeroPreviewAdapter;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AddHeroDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.AllUserHeroesDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.DotsMenuFragmentFromCurrnetUserActivity;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;

    private SharedViewModel sharedViewModel;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private ImageView ic_dots_menu,ic_my_hero;
    private TextView tv_user_name_and_surname, tv_user_id;
    private CircleImageView civ_user_avatar;
    private RecyclerView rv_hero_preview_holder;

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
        rv_hero_preview_holder=binding.rvHeroPreviewHolder;
        ic_my_hero=binding.icMyHero;

        setAdapter();

        UserData userData = null;
        
        if (sharedViewModel.getCurrentUserDataLiveData().getValue()!=null) {
            userData = sharedViewModel.getCurrentUserDataLiveData().getValue();
            if (userData.getUserName()!=null && userData.getUserSurname()!=null) tv_user_name_and_surname.setText(userData.getUserName()+" "+userData.getUserSurname());
            if (userData.getUserId()!=null && !userData.getUserId().isEmpty()) tv_user_id.setText("@"+userData.getUserId());
        }
    }

    private void setAdapter(){
        List<HeroData> heroes = new ArrayList<>();
        heroes.add(null);
        HeroPreviewAdapter adapter = new HeroPreviewAdapter(heroes,getParentFragmentManager());

        binding.rvHeroPreviewHolder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvHeroPreviewHolder.setAdapter(adapter);
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

        ic_my_hero.setOnClickListener(v->{
            FragmentManager fragmentManager = getParentFragmentManager();
            AllUserHeroesDialogFragment dialogFragment = new AllUserHeroesDialogFragment();
            dialogFragment.show(fragmentManager, "my_dialog");
        });
    }
}