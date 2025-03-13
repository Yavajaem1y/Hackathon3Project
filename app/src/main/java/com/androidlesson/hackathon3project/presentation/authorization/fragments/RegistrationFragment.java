package com.androidlesson.hackathon3project.presentation.authorization.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentRegistrationBinding;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.registratoinViewModel.RegistrationFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.registratoinViewModel.RegistrationFragmentViewModelFactory;

import javax.inject.Inject;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    private RegistrationFragmentViewModel vm;
    @Inject
    RegistrationFragmentViewModelFactory vmFactory;

    private MainActivityViewModel parentVM;
    @Inject
    MainActivityViewModelFactory parentVMFactory;

    private EditText et_email,et_name,et_surname,et_id,et_password,et_repassword;
    private TextView b_reg,tv_password_error;
    private RelativeLayout rl_progress_bar;
    private LinearLayout ll_go_to_log;
    private ImageView iv_show_password, iv_show_repassword;

    private Boolean passwordErrorVisibility=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentRegistrationBinding.inflate(inflater, container, false);

        initialization();

        setOnClickListener();

        observe();

        addTextChangedListener();

        return binding.getRoot();
    }

    private void initialization(){
        ((App) requireActivity().getApplication()).appComponent.injectRegistrationFragment(this);

        vm=new ViewModelProvider(this,vmFactory).get(RegistrationFragmentViewModel.class);
        parentVM=new ViewModelProvider(requireActivity(),parentVMFactory).get(MainActivityViewModel.class);

        et_id=binding.etId;
        et_name=binding.etName;
        et_surname=binding.etSurname;
        et_repassword=binding.etRepassword;
        et_email=binding.etEmail;
        et_password=binding.etPassword;
        b_reg=binding.bReg;
        rl_progress_bar=binding.rlWithProgressBar;
        ll_go_to_log=binding.llGoToLog;
        iv_show_password=binding.ivShowPassword;
        iv_show_repassword=binding.ivShowRepassword;
        tv_password_error=binding.tvPasswordError;
    }

    private void setOnClickListener(){
        b_reg.setOnClickListener(v->{
            String id=et_id.getText().toString().trim();
            String name=et_name.getText().toString().trim();
            String surname=et_surname.getText().toString().trim();
            String email=et_email.getText().toString().trim();
            String password=et_password.getText().toString().trim();
            String repassword=et_repassword.getText().toString().trim();

            rl_progress_bar.setVisibility(View.VISIBLE);

            if (password.length()<8) {
                passwordErrorVisibility = true;
                tv_password_error.setVisibility(View.VISIBLE);
            }

            vm.registration(new DataToRegistration(email,password,repassword,name,surname,id));
        });

        ll_go_to_log.setOnClickListener(v->{
            parentVM.go_to_login();
        });
    }

    private void observe(){
        vm.getErrorCallbackMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                rl_progress_bar.setVisibility(View.INVISIBLE);
            }
        });

        vm.getUserDataMutableLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null) parentVM.checkUserAuthorization();
                rl_progress_bar.setVisibility(View.INVISIBLE);
            }
        });

        final boolean[] isPasswordVisible = {false};
        iv_show_password.setOnClickListener(v->{
            if (isPasswordVisible[0]) {
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iv_show_password.setImageResource(R.drawable.ic_hide_password);
            } else {
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                iv_show_password.setImageResource(R.drawable.ic_show_password);
            }
            isPasswordVisible[0] = !isPasswordVisible[0];
            et_password.setSelection(et_password.getText().length());
        });

        final boolean[] isRepasswordVisible = {false};
        iv_show_repassword.setOnClickListener(v->{
            if (isRepasswordVisible[0]) {
                et_repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iv_show_repassword.setImageResource(R.drawable.ic_hide_password);
            } else {
                et_repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                iv_show_repassword.setImageResource(R.drawable.ic_show_password);
            }
            isRepasswordVisible[0] = !isRepasswordVisible[0];
            et_repassword.setSelection(et_repassword.getText().length());
        });
    }

    private void addTextChangedListener(){
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 8 && passwordErrorVisibility) {
                    tv_password_error.setVisibility(View.VISIBLE);
                } else {
                    tv_password_error.setVisibility(View.GONE);
                    passwordErrorVisibility=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}