package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.HeroItemPreview;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.adapters.AddImageAdapter;
import com.androidlesson.hackathon3project.presentation.main.adapters.HeroPreviewAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel.AllUserHeroesViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel.AllUserHeroesViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class AllUserHeroesDialogFragment extends DialogFragment {

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private AllUserHeroesViewModel vm;
    @Inject
    AllUserHeroesViewModelFactory vmFactory;

    private RecyclerView rv_hero_preview_holder;
    private ImageView b_back;
    private HeroPreviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_all_user_hero, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectAllUserHeroesDialogFragment(this);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(AllUserHeroesViewModel.class);

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
        b_back=view.findViewById(R.id.b_back);
        rv_hero_preview_holder=view.findViewById(R.id.rv_heroes_holder);
        setAdapter(view);
    }

    private void setAdapter(View view){
        adapter=new HeroPreviewAdapter(getParentFragmentManager());

        rv_hero_preview_holder.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_hero_preview_holder.setAdapter(adapter);
    }

    private void setOnClickListener(){
        b_back.setOnClickListener(v->{
            dismiss();
        });
    }

    private void observe(){
        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                vm.setCurrentUser(userData);
            }
        });

        vm.getHeroesPreviewMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<HeroItemPreview>>() {
            @Override
            public void onChanged(List<HeroItemPreview> heroItemPreviews) {
                adapter.setNewPreviews(heroItemPreviews);
            }
        });
    }
}

