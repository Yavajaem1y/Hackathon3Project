package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToEdit;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnDataPass;
import com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel.EditUserProfileFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel.EditUserProfileFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.bumptech.glide.Glide;

import java.io.IOException;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditUserProfileDialogFragment extends DialogFragment {

    private EditText et_user_name, et_user_surname, et_user_id, et_user_email;
    private ImageView iv_cancellation;
    private CircleImageView civ_avatar;
    private TextView b_edit_profile,tv_name_and_surname,tv_id;
    private RelativeLayout rl_with_progress_bar, b_edit_avatar;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private EditUserProfileFragmentViewModel vm;
    @Inject
    EditUserProfileFragmentViewModelFactory vmFactory;

    private UserData data;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_edit_user_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();

                        try {
                            Context context = getContext();
                            if (context == null) return;

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                            vm.setUserAvatarMutableLiveData(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectEditUserProfileFragment(this);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(this,vmFactory).get(EditUserProfileFragmentViewModel.class);

        initialization(view);

        setOnClickListener();

        observe();

        addTextChangedListener();

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
        et_user_email=view.findViewById(R.id.et_email);
        et_user_name=view.findViewById(R.id.et_name);
        et_user_surname=view.findViewById(R.id.et_surname);
        et_user_id=view.findViewById(R.id.et_id);
        iv_cancellation=view.findViewById(R.id.iv_cancellation);
        b_edit_profile=view.findViewById(R.id.b_edit_profile);
        rl_with_progress_bar=view.findViewById(R.id.rl_with_progress_bar);
        tv_name_and_surname=view.findViewById(R.id.tv_curr_user_name_and_surname);
        tv_id=view.findViewById(R.id.tv_login);
        b_edit_avatar=view.findViewById(R.id.b_edit_avatar);
        civ_avatar=view.findViewById(R.id.civ_curr_user_avatar);

        String sharedEmail=sharedVM.getUserEmailMutableLiveData().getValue();
        if(sharedEmail!=null && !sharedEmail.isEmpty()){
            et_user_email.setText(sharedEmail);
        }

        data=sharedVM.getCurrentUserDataLiveData().getValue();
        if (data!=null){
            et_user_name.setText(data.getUserName());
            et_user_surname.setText(data.getUserSurname());
            et_user_id.setText(data.getUserId());
            if (data.getImageData()!=null) Glide.with(getContext()).load(data.getImageData()).into(civ_avatar);

            vm.setUserNameMutableLiveData(data.getUserName());
            vm.setUserSurnameMutableLiveData(data.getUserSurname());
            vm.setUserIdMutableLiveData(data.getUserId());
        }
    }

    private void setOnClickListener(){
        iv_cancellation.setOnClickListener(v->{
            dismiss();
        });

        b_edit_profile.setOnClickListener(v->{
            String name=et_user_name.getText().toString().trim();
            String surname=et_user_surname.getText().toString().trim();
            String id=et_user_id.getText().toString().trim();
            String email=et_user_email.getText().toString().trim();

            String sharedEmail=sharedVM.getUserEmailMutableLiveData().getValue();
            if(sharedEmail!=null && !sharedEmail.isEmpty()) {
                rl_with_progress_bar.setVisibility(View.VISIBLE);
                vm.editData(new UserDataToEdit(id, name, surname, email, sharedEmail, data.getUserId(),data.getUserSystemId()));
            }
        });

        b_edit_avatar.setOnClickListener(v->{
            pickImageFromGallery();
        });
    }

    private void observe(){
        vm.getErrorCallbackMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                rl_with_progress_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        vm.getUserDataMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                rl_with_progress_bar.setVisibility(View.GONE);
                if (userData!=null) {
                    sharedVM.setCurrentUserData(userData);
                    dismiss();
                }
            }
        });

        vm.getUserNameAndSurnameMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_name_and_surname.setText(s);
            }
        });

        vm.getUserIdMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_id.setText("@"+s);
            }
        });

        vm.getUserAvatarMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                Glide.with(getContext()).load(bitmap).into(civ_avatar);
            }
        });
    }

    private void addTextChangedListener(){
        et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setUserNameMutableLiveData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_user_surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setUserSurnameMutableLiveData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setUserIdMutableLiveData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}

