package com.androidlesson.hackathon3project.presentation.main.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel.MainActivityViewModel;
import com.androidlesson.hackathon3project.presentation.main.adapters.MapArticleItemsAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.pointDetailsActivityViewModel.PointDetailsActivityViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.pointDetailsActivityViewModel.PointDetailsActivityViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class PointDetailsActivity extends AppCompatActivity {

    private ImageView b_back;
    private TextView tv_name;
    private RecyclerView recyclerView;
    private TextView b_resume;

    private PointDetailsActivityViewModel vm;
    @Inject
    PointDetailsActivityViewModelFactory vmFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_details);

        ((App) this.getApplication()).appComponent.injectPointDetailsActivity(this);
        vm= new ViewModelProvider(this,vmFactory).get(PointDetailsActivityViewModel.class);

        setWindowParam();

        initializaiton();

        setOnClickListener();
    }

    private void setWindowParam(){
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#2C2D33"));

        window.setNavigationBarColor(Color.parseColor("#2C2D33"));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {

                insetsController.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
            }
        }
    }

    private void initializaiton(){
        tv_name = findViewById(R.id.tv_point_name);
        b_back=findViewById(R.id.b_back);
        b_resume=findViewById(R.id.b_resume);
        recyclerView = findViewById(R.id.recyclerView_items);

        String pointName = getIntent().getStringExtra("POINT_NAME");
        String userId = getIntent().getStringExtra("USER_ID");
        List<MapArticleItem> pointItems = (List<MapArticleItem>) getIntent().getSerializableExtra("POINT_ITEMS");
        int pointId=getIntent().getIntExtra("POINT_ID",0);
        int userLastPoint=getIntent().getIntExtra("USER_LAST_POINT",0);
        int moduleSize=getIntent().getIntExtra("MODULE_SIZE",0);

        vm.setValues(pointName,pointItems,pointId,moduleSize,userId,userLastPoint);
        tv_name.setText((pointName!=null && !pointName.isEmpty())? pointName : vm.pointName);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MapArticleItemsAdapter adapter = new MapArticleItemsAdapter(this, pointItems!=null? pointItems: vm.pointItems);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(){
        b_back.setOnClickListener(v->{
            finish();
        });

        b_resume.setOnClickListener(v->{
            vm.unlockNextPoint();
            finish();
        });
    }
}