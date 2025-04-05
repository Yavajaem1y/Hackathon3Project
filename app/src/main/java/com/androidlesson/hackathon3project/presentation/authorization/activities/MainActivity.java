package com.androidlesson.hackathon3project.presentation.authorization.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
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
    private VideoView vv_app_preview;

    @Inject
    MainActivityViewModelFactory vmFactory;
    @Inject
    SharedViewModelFactory sharedViewModelFactory;

    private RelativeLayout mainRelativeLayout;
    private TextView tv_go_to_login,tv_go_to_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#403328"));

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

        vv_app_preview = findViewById(R.id.vv_app_preview);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_preview);
        vv_app_preview.setVideoURI(uri);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        vv_app_preview.setOnPreparedListener(mp -> {
            int videoWidth = mp.getVideoWidth();
            int videoHeight = mp.getVideoHeight();

            // Подстраиваем размер видео в зависимости от пропорций экрана
            float scaleX = (float) screenWidth / videoWidth;
            float scaleY = (float) screenHeight / videoHeight;

            // Масштабируем видео
            vv_app_preview.setLayoutParams(new RelativeLayout.LayoutParams(
                    (int) (videoWidth * scaleX),
                    (int) (videoHeight * scaleY)
            ));

            vv_app_preview.start();
        });

        vv_app_preview.setMediaController(null);
        vv_app_preview.requestFocus();

        new Handler().postDelayed(() -> {
            tv_go_to_login.setVisibility(View.VISIBLE);
            tv_go_to_registration.setVisibility(View.VISIBLE);

            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            fadeIn.setDuration(1000);

            tv_go_to_login.startAnimation(fadeIn);
            tv_go_to_registration.startAnimation(fadeIn);
        }, 5000);
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