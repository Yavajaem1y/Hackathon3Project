package com.androidlesson.hackathon3project.presentation.authorization.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.userSessionCheckerActivityViewModel.UserSessionCheckerActivityViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.userSessionCheckerActivityViewModel.UserSessionCheckerActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnDataPass;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class UserSessionCheckerActivity extends AppCompatActivity implements OnDataPass {

    private UserSessionCheckerActivityViewModel vm;
    private SharedViewModel sharedViewModel;
    @Inject
    UserSessionCheckerActivityViewModelFactory vmFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private FrameLayout fl_container;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_session_check);

        initialization();

        observe();
    }

    @Override
    protected void onStart() {
        super.onStart();

        vm.checkUserAuthorization();
    }

    private void initialization(){
        ((App) this.getApplication()).appComponent.injectUserSessionCheckerActivity(this);

        vm= new ViewModelProvider(this,vmFactory).get(UserSessionCheckerActivityViewModel.class);
        sharedViewModel=new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);

        fl_container=findViewById(R.id.fl_fragment_container);
        progressBar=findViewById(R.id.pb_progress_bar);

    }

    private void observe(){
        vm.getActivityToActionLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==0) {
                    startActivity(new Intent(UserSessionCheckerActivity.this,AuthorizationActivity.class));
                }
                else if (integer==1){
                    startActivity(new Intent(UserSessionCheckerActivity.this,SetUserDataActivity.class));
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    sharedViewModel.setCurrentUserData(vm.getUserDataLiveData().getValue());
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, new MainFragment()).commit();
                }
            }
        });
    }

    @Override
    public void onDataPass(String data) {
        vm.logOut();
        finish();
    }
}