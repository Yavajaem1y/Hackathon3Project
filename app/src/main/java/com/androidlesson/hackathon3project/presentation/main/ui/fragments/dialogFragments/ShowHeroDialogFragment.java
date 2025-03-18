package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroData;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.adapters.ImageAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel.ShowHeroViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showHeroViewModel.ShowHeroViewModelFactory;
import com.bumptech.glide.Glide;

import javax.inject.Inject;


public class ShowHeroDialogFragment extends DialogFragment {

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private ShowHeroViewModel vm;
    @Inject
    ShowHeroViewModelFactory vmFactory;

    private String heroId;
    private ImageAdapter adapter;

    private ImageView iv_hero_image,iv_cancellation;
    private TextView tv_hero_name, tv_hero_age, tv_hero_info, tv_hero_number_of_proud, b_proud;
    private RecyclerView rv_hero_additional_image_holder;

    public ShowHeroDialogFragment(String heroId) {
        this.heroId = heroId;
    }

    public ShowHeroDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_show_hero, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectShowHeroFragment(this);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(ShowHeroViewModel.class);

        initialization(view);

        setOnClickListener();

        observe();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Dialog dialog = getDialog();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }


    private void initialization(View view){
        tv_hero_age=view.findViewById(R.id.tv_hero_age);
        tv_hero_name=view.findViewById(R.id.tv_hero_name);
        tv_hero_info=view.findViewById(R.id.tv_hero_info);
        tv_hero_number_of_proud=view.findViewById(R.id.tv_hero_number_of_proud);
        b_proud=view.findViewById(R.id.b_proud);
        iv_cancellation=view.findViewById(R.id.iv_cancellation);
        iv_hero_image=view.findViewById(R.id.iv_hero_image);
        rv_hero_additional_image_holder=view.findViewById(R.id.rv_hero_additional_image_holder);

        if (heroId!=null) vm.setHeroId(heroId);
        setAdapter();
    }

    private void setAdapter(){
        adapter=new ImageAdapter(getContext());
        rv_hero_additional_image_holder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_hero_additional_image_holder.setAdapter(adapter);
    }

    private void setOnClickListener(){
        iv_cancellation.setOnClickListener(v->{
            vm.dismiss();
            dismiss();
        });
        b_proud.setOnClickListener(v->{
            vm.proud();
        });
    }

    private void observe(){
        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                vm.setCurrentUser(userData);
            }
        });

        vm.getHeroDataMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HeroData>() {
            @Override
            public void onChanged(HeroData heroData) {
                if (heroData!=null) {
                    tv_hero_name.setText(heroData.getHeroName());
                    tv_hero_info.setText(heroData.getHeroInfo());

                    if (heroData.getHeroAvatarImage() != null && !heroData.getHeroAvatarImage().isEmpty())
                        Glide.with(getContext()).load(heroData.getHeroAvatarImage()).into(iv_hero_image);

                    if (heroData.getListProud() != null) {
                        tv_hero_number_of_proud.setText("Число пользователей, котрые горядтся данным героем: " + heroData.getListProud().size());
                    }

                    if (heroData.getHeroAdditionalImages() != null && !heroData.getHeroAdditionalImages().isEmpty()) {
                        adapter.setImages(heroData.getHeroAdditionalImages());
                    } else {
                        rv_hero_additional_image_holder.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}

