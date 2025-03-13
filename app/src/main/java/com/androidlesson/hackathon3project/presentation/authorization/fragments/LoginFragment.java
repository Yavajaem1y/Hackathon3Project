package com.androidlesson.hackathon3project.presentation.authorization.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentLoginBinding;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.loginViewModel.LoginFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.loginViewModel.LoginFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModelFactory;

import java.util.Locale;

import javax.inject.Inject;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private LoginFragmentViewModel vm;
    private MainActivityViewModel parentVM;
    @Inject
    LoginFragmentViewModelFactory vmFactory;
    @Inject
    MainActivityViewModelFactory parentVMFactory;

    private TextView b_login;
    private LinearLayout ll_go_to_reg;
    private EditText et_email, et_password;
    private RelativeLayout rl_progress_bar;
    private ImageView iv_show_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLoginBinding.inflate(inflater, container, false);

        initialization();

        setOnClickListener();

        observe();

        return binding.getRoot();
    }

    private void initialization(){
        ((App) requireActivity().getApplication()).appComponent.injectLoginFragment(this);

        vm=new ViewModelProvider(this,vmFactory).get(LoginFragmentViewModel.class);
        parentVM=new ViewModelProvider(requireActivity(),parentVMFactory).get(MainActivityViewModel.class);

        b_login=binding.bLog;
        ll_go_to_reg=binding.llGoToReg;
        et_email=binding.etEmail;
        et_password=binding.etPassword;
        rl_progress_bar=binding.rlWithProgressBar;
        iv_show_password=binding.ivShowPassword;
    }

    private void setOnClickListener(){
        b_login.setOnClickListener(v->{
            rl_progress_bar.setVisibility(View.VISIBLE);
            vm.login(new DataToLogin(et_email.getText().toString().toLowerCase(Locale.ROOT).trim(),et_password.getText().toString().trim()));
        });

        ll_go_to_reg.setOnClickListener(v->{
            parentVM.go_to_registration();
        });
    }

    private void observe(){
        vm.getErrorMessageMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                rl_progress_bar.setVisibility(View.GONE);
            }
        });

        vm.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                rl_progress_bar.setVisibility(View.GONE);
                if (aBoolean) parentVM.checkUserAuthorization();
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
    }
}