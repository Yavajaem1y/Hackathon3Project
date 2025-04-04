package com.androidlesson.hackathon3project.presentation.main.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsetsController;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.adapters.OnboardingAdapter;
import com.androidlesson.hackathon3project.presentation.main.decoration.OvalIndicatorDecoration;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Установка цвета статус-бара (верхняя панель)
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#2C2D33")); // Пример цвета

        // Установка цвета навигационной панели (нижняя панель)
        window.setNavigationBarColor(Color.parseColor("#202124")); // Пример цвета

        // Для более старых версий Android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                // Убираем флаг для темных значков и текста
                insetsController.setSystemBarsAppearance(
                        0, // Убираем флаг для светлых значков
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);  // Убираем светлые значки (сделать текст и иконки белыми)
            }
        }


        // Инициализация ViewPager
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addItemDecoration(new OvalIndicatorDecoration());
    }
}