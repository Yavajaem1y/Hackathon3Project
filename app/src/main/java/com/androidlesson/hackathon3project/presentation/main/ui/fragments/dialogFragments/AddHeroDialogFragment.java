package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.adapters.AddImageAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class AddHeroDialogFragment extends DialogFragment {

    private AddHeroFragmentViewModel vm;
    @Inject
    AddHeroFragmentViewModelFactory vmFactory;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private EditText et_hero_name,et_hero_info,et_date;
    private ImageView iv_hero_avatar_image,iv_cancellation;
    private RecyclerView rv_hero_additional_image_holder;
    private TextView b_add_hero;
    private RelativeLayout rl_edit_hero_avatar_image,rl_progress_bar;
    private CardView cv_image;

    private AddImageAdapter adapter;

    private ActivityResultLauncher<Intent> avatarImagePickerLauncher;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_hero, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avatarImagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();

                        try {
                            Context context = getContext();
                            if (context == null) return;

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                            vm.addAvatarImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        vm.addImage(result.getData().getData());
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectAddHeroFragment(this);
        vm=new ViewModelProvider(this,vmFactory).get(AddHeroFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

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
        et_hero_name=view.findViewById(R.id.et_name);
        et_hero_info=view.findViewById(R.id.et_info);
        iv_hero_avatar_image=view.findViewById(R.id.iv_hero_image);
        rv_hero_additional_image_holder=view.findViewById(R.id.rv_hero_additional_image_holder);
        b_add_hero=view.findViewById(R.id.b_add_hero);
        rl_edit_hero_avatar_image=view.findViewById(R.id.rl_with_avatar_image);
        iv_cancellation=view.findViewById(R.id.iv_cancellation);
        rl_progress_bar=view.findViewById(R.id.rl_with_progress_bar);
        cv_image=view.findViewById(R.id.cv_image);
        et_date=view.findViewById(R.id.et_date);

        setAdapter();
    }

    private void setAdapter(){
        List<Uri> images = new ArrayList<>();
        images.add(null);
        adapter=new AddImageAdapter(images, new AddImageAdapter.OnAddImageClickListener() {
            @Override
            public void onAddImageClick() {
                pickImageFromGallery();
            }
        });
        rv_hero_additional_image_holder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_hero_additional_image_holder.setAdapter(adapter);
    }

    private void setOnClickListener(){
        b_add_hero.setOnClickListener(v->{
            rl_progress_bar.setVisibility(View.VISIBLE);

            String date=et_date.getText().toString();
            String name=et_hero_name.getText().toString().trim();
            String info = et_hero_info.getText().toString().trim();

            vm.addNewHero(new HeroDataToDb(name,info,date));
        });

        rl_edit_hero_avatar_image.setOnClickListener(v->{
            pickAvatarImageFromGallery();
        });

        iv_cancellation.setOnClickListener(v->{
            dismiss();
        });

    }

    private void observe(){
        vm.getHeroAvatarImageMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                cv_image.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(bitmap).override(1000).centerCrop().into(iv_hero_avatar_image);
            }
        });

        vm.getImagesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Uri>>() {
            @Override
            public void onChanged(List<Uri> images) {
                adapter.setNewImages(images);
            }
        });

        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                vm.setCurrentUser(userData);
            }
        });

        vm.getErrorCallbackMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                rl_progress_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
            }
        });

        vm.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                rl_progress_bar.setVisibility(View.GONE);
                if (aBoolean) dismiss();
            }
        });
    }

    public void pickAvatarImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        avatarImagePickerLauncher.launch(intent);
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}

