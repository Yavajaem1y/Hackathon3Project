package com.androidlesson.hackathon3project.presentation.authorization.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnDataPass;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.MainFragment;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements OnDataPass {

    private MainActivityViewModel vm;
    private SharedViewModel sharedViewModel;
    @Inject
    MainActivityViewModelFactory vmFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private RelativeLayout mainRelativeLayout;
    private TextView tv_go_to_login,tv_go_to_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        observe();

        OnBackPressedDispatcher();

        setClickListener();
    }

    private void initialization(){
        ((App) this.getApplication()).appComponent.injectUserSessionCheckerActivity(this);

        vm= new ViewModelProvider(this,vmFactory).get(MainActivityViewModel.class);
        sharedViewModel=new ViewModelProvider(this,sharedViewModelFactory).get(SharedViewModel.class);


        mainRelativeLayout=findViewById(R.id.rl_main_in_layout);
        tv_go_to_login=findViewById(R.id.b_log);
        tv_go_to_registration=findViewById(R.id.b_reg);

    }

    private void observe(){
        vm.getSelectedFragmentMutableLiveData().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment!=null){
                    Log.d("MainActivity",fragment.getClass().getName());
                    if (fragment.getClass() == MainFragment.class) {
                        sharedViewModel.setCurrentUserData(vm.getUserDataLiveData().getValue());
                    }
                    mainRelativeLayout.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container)).commit();
                    mainRelativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setClickListener(){
        tv_go_to_registration.setOnClickListener(v->{
            vm.go_to_registration();
        });

        tv_go_to_login.setOnClickListener(v->{
            vm.go_to_login();
        });
    }

    private void OnBackPressedDispatcher(){
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                vm.removeFragment();
            }
        });
    }

    @Override
    public void onDataPass(String data) {
        vm.logOut();
        vm.removeFragment();
    }

}