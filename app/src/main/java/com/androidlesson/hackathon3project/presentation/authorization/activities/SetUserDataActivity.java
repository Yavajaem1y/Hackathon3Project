package com.androidlesson.hackathon3project.presentation.authorization.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.setUserDataActivityViewModel.SetUserDataActivityViewModel;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.setUserDataActivityViewModel.SetUserDataActivityViewModelFactory;

import javax.inject.Inject;

public class SetUserDataActivity extends AppCompatActivity {

    private EditText et_name,et_surname,et_user_id;
    private TextView b_save_data;

    private SetUserDataActivityViewModel vm;
    @Inject
    SetUserDataActivityViewModelFactory vmFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_data);

        initialization();

        setObserver();

        setOnClickListener();
    }

    private void initialization() {
        ((App) getApplication()).appComponent.injectSetUserDataActivity(this);
        vm=new ViewModelProvider(this,vmFactory).get(SetUserDataActivityViewModel.class);

        getOnBackPressedDispatcher().addCallback(this, callback);

        et_name=findViewById(R.id.et_user_name);
        et_surname=findViewById(R.id.et_user_surname);
        b_save_data=findViewById(R.id.b_save_user_data);
        et_user_id=findViewById(R.id.et_user_id);
    }

    private void setObserver() {
        vm.getUserDataLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData!=null){
                    finish();
                }
            }
        });

        vm.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
    }

    private void setOnClickListener() {
        b_save_data.setOnClickListener(v->{
            String name=et_name.getText().toString().trim();
            String surname=et_surname.getText().toString().trim();
            String userId=et_user_id.getText().toString().trim();
            vm.SaveUserData(new UserData(name,surname,null,userId));
        });
    }

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
        }
    };

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}