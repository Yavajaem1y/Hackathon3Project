package com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.main.models.Question;
import com.androidlesson.domain.main.useCase.AddMedalToUserUseCase;
import com.androidlesson.domain.main.useCase.UnlockTheNextPointUseCase;

import java.util.ArrayList;
import java.util.List;

public class TestActivityViewModel extends ViewModel {
    private AddMedalToUserUseCase addMedalToUserUseCase;
    private UnlockTheNextPointUseCase unlockTheNextPointUseCase;

    public String userId;
    private int moduleSize,userLastPoint,pointsCompleted;
    public int pointId;

    private final MutableLiveData<Integer> currentQuestionIndexMutableLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> correctAnswersMutableLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<List<Question>> questionsMutableLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> selectedAnswerIndex = new MutableLiveData<>(-1);
    private final MutableLiveData<Boolean> isAnswerSubmitted = new MutableLiveData<>(false);

    public TestActivityViewModel(AddMedalToUserUseCase addMedalToUserUseCase, UnlockTheNextPointUseCase unlockTheNextPointUseCase) {
        this.addMedalToUserUseCase = addMedalToUserUseCase;
        this.unlockTheNextPointUseCase=unlockTheNextPointUseCase;
    }

    public void setListQuestions(List<Question> questions){
        questionsMutableLiveData.setValue(questions);
    }

    public void checkAnswer(int selectedId){
        if (Boolean.TRUE.equals(isAnswerSubmitted.getValue())) return;

        selectedAnswerIndex.setValue(selectedId);
        isAnswerSubmitted.setValue(true);

        int correctIndex = questionsMutableLiveData.getValue().get(currentQuestionIndexMutableLiveData.getValue()).getCorrectAnswerIndex();
        if (correctIndex == selectedId){
            int correctAnswers = correctAnswersMutableLiveData.getValue();
            correctAnswersMutableLiveData.setValue(correctAnswers + 1);
        }
    }

    public void unlockTheNextPoint(){
        if (pointId!=0 && moduleSize!=0 && userId!=null){
            if (userLastPoint==pointId) {
                unlockTheNextPointUseCase.execute(userId, pointId, moduleSize, pointsCompleted);
            }
        }
    }

    public void addMedalToUser(int medalId){
        if (userId!=null){
            addMedalToUserUseCase.execute(userId,medalId);
        }
    }

    public void setValues(int pointId, int moduleSize, String userId, int userLastPoint, int pointsCompleted){
        this.moduleSize=moduleSize;
        this.pointId=pointId;
        this.userId=userId;
        this.userLastPoint=userLastPoint;
        this.pointsCompleted=pointsCompleted;
    }


    public void nextQuestion(){
        Integer current = currentQuestionIndexMutableLiveData.getValue();
        currentQuestionIndexMutableLiveData.setValue(current + 1);
        selectedAnswerIndex.setValue(-1);
        isAnswerSubmitted.setValue(false);
    }

    public LiveData<Integer> getCurrentQuestionIndexMutableLiveData() {
        return currentQuestionIndexMutableLiveData;
    }

    public LiveData<Integer> getCorrectAnswersMutableLiveData() {
        return correctAnswersMutableLiveData;
    }

    public LiveData<List<Question>> getQuestionsMutableLiveData() {
        return questionsMutableLiveData;
    }

    public LiveData<Integer> getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public LiveData<Boolean> getIsAnswerSubmitted() {
        return isAnswerSubmitted;
    }
}
