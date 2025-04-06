package com.androidlesson.hackathon3project.presentation.main.ui.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.MapArticleItem;
import com.androidlesson.domain.main.models.Question;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.TestExitDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments.TestResultDialogFragment;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TestActivity extends AppCompatActivity {
    private TextView questionText, nextButton,progress_text;
    private TextView[] optionButtons = new TextView[4];
    private View progressView;
    private ImageView b_cancellation;
    private FrameLayout progressContainer;

    private TestActivityViewModel vm;

    @Inject
    TestActivityViewModelFactory vmFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ((App) getApplication()).appComponent.injectTestActivity(this);
        vm = new ViewModelProvider(this, vmFactory).get(TestActivityViewModel.class);

        initializeViews();

        String userId = getIntent().getStringExtra("USER_ID");
        int pointId=getIntent().getIntExtra("POINT_ID",0);
        int userLastPoint=getIntent().getIntExtra("USER_LAST_POINT",0);
        int moduleSize=getIntent().getIntExtra("MODULE_SIZE",0);
        int pointsCompleted=getIntent().getIntExtra("USER_POINTS_COMPLETED",0);

        vm.setValues(pointId,moduleSize,userId,userLastPoint,pointsCompleted);

        if (vm.getQuestionsMutableLiveData().getValue().isEmpty()) {
            List<Question> pointItems = (List<Question>) getIntent().getSerializableExtra("POINT_QUESTIONS");
            vm.setListQuestions(pointItems);
        }

        setOnClickListeners();
        observeViewModel();
    }

    private void initializeViews() {
        questionText = findViewById(R.id.question_text);
        optionButtons[0] = findViewById(R.id.option1);
        optionButtons[1] = findViewById(R.id.option2);
        optionButtons[2] = findViewById(R.id.option3);
        optionButtons[3] = findViewById(R.id.option4);
        nextButton = findViewById(R.id.next_button);
        progress_text=findViewById(R.id.progress_text);
        progressView = findViewById(R.id.progress_view);
        progressContainer = findViewById(R.id.progress_container);
        b_cancellation=findViewById(R.id.b_cancellation);

        nextButton.setClickable(false);
        nextButton.setBackgroundResource(R.drawable.bg_rounded_12dp_grey);
    }

    private void showQuestion(int index) {
        List<Question> questions = vm.getQuestionsMutableLiveData().getValue();
        if (index >= questions.size()) {
            showResultDialog();
            return;
        }

        Question q = questions.get(index);
        questionText.setText(q.getQuestionText());
        List<String> options = q.getOptions();

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options.get(i));
            optionButtons[i].setBackgroundResource(R.drawable.bg_rounded_8dp_grey);
            optionButtons[i].setClickable(true);
        }

        nextButton.setClickable(false);
        nextButton.setBackgroundResource(R.drawable.bg_rounded_12dp_grey);
    }

    private void setOnClickListeners() {
        for (int i = 0; i < 4; i++) {
            final int selectedIndex = i;
            optionButtons[i].setOnClickListener(v -> vm.checkAnswer(selectedIndex));
        }

        nextButton.setOnClickListener(v -> vm.nextQuestion());
        b_cancellation.setOnClickListener(v->{
            TestExitDialogFragment resultDialog= new TestExitDialogFragment();
            resultDialog.setCancelable(false);
            resultDialog.show(getSupportFragmentManager(), "TestExit");
        });
    }

    private void observeViewModel() {
        vm.getCurrentQuestionIndexMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                showQuestion(index);
                updateProgressBar(index, vm.getQuestionsMutableLiveData().getValue().size());
            }
        });

        vm.getSelectedAnswerIndex().observe(this, selectedIndex -> {
            if (selectedIndex == null || selectedIndex == -1) return;

            int currentIndex = vm.getCurrentQuestionIndexMutableLiveData().getValue();
            Question question = vm.getQuestionsMutableLiveData().getValue().get(currentIndex);
            int correctIndex = question.getCorrectAnswerIndex();

            for (int i = 0; i < 4; i++) {
                optionButtons[i].setClickable(false);

                if (i == selectedIndex) {
                    if (i == correctIndex) {
                        optionButtons[i].setBackgroundResource(R.drawable.bg_rounded_8dp_accent);
                    } else {
                        optionButtons[i].setBackgroundResource(R.drawable.bg_rounded_8dp_red);
                    }
                } else {
                    optionButtons[i].setBackgroundResource(R.drawable.bg_rounded_8dp_grey);
                }
            }

            nextButton.setClickable(true);
            nextButton.setBackgroundResource(R.drawable.bg_rounded_12dp_accent);
        });
    }

    private void showResultDialog() {
        int correctAnswers = vm.getCorrectAnswersMutableLiveData().getValue();
        int totalQuestions = vm.getQuestionsMutableLiveData().getValue().size();

        TestResultDialogFragment resultDialog = new TestResultDialogFragment(correctAnswers, totalQuestions);
        resultDialog.setCancelable(false);
        resultDialog.show(getSupportFragmentManager(), "ResultBottomSheet");
    }

    private void updateProgressBar(int currentIndex, int totalQuestions) {
        progress_text.setText(currentIndex+"/"+totalQuestions);
        progressContainer.post(() -> {
            int fullWidth = progressContainer.getWidth();

            float percent = (float)(currentIndex+1) / (totalQuestions+1);

            float totalParts = totalQuestions;
            float partWidth = fullWidth / totalParts;

            int progressWidth = (int)(percent * totalParts * partWidth);

            if (progressWidth > fullWidth) {
                progressWidth = fullWidth;
            }

            ValueAnimator animator = ValueAnimator.ofInt(progressView.getLayoutParams().width, progressWidth);
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator());

            // Обновляем LayoutParams во время анимации
            animator.addUpdateListener(animation -> {
                int width = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = progressView.getLayoutParams();
                params.width = width;
                progressView.setLayoutParams(params);
            });

            animator.start();
        });
    }

}