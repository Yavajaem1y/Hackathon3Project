package com.androidlesson.hackathon3project.presentation.main.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.training.OnboardingFragment;

public class OnboardingAdapter extends FragmentStateAdapter {
    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        boolean isLastPage = (position == getItemCount() - 1);
        switch (position) {
            case 0:
                return OnboardingFragment.newInstance(
                        R.drawable.student, "Проходите разделы",
                        "читайте статьи и выполняйте\nмини-тесты для закрепления\nзнаний",
                        "Добро пожаловать в\nинтерактивное\nпутешествие по Великой\nОтечественной войне!",
                        isLastPage
                );
            case 1:
                return OnboardingFragment.newInstance(
                        R.drawable.statiastateika, "Изучайте истории героев",
                        "их подвиги навсегда остались в памяти народа",
                        null,
                        isLastPage
                );
            case 2:
                return OnboardingFragment.newInstance(
                        R.drawable.hitakakayato, "Финальный экзамен",
                        "после изучения всех материалов раздела сдайте тест из 10 вопросов, чтобы открыть следующий этап",
                        null,
                        isLastPage
                );
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}