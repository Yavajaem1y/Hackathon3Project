package com.androidlesson.hackathon3project.presentation.main.models;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;

import java.util.ArrayList;
import java.util.List;

public class MedalsHolder {
    private List<Medal> medals=new ArrayList<>();

    public MedalsHolder() {
        medals.add(new Medal("Медаль за начало","Поздравляем вы получили медаль за прохождение начального раздела", R.drawable.ic_start_medal));
        medals.add(new Medal("Битва за Москву","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Москву", R.drawable.ic_moscow_medal));
        medals.add(new Medal("Битва за Ленинград","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Ленинград", R.drawable.ic_medal_leningrad));
        medals.add(new Medal("Битва за Сталинград","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Сталинград", R.drawable.ic_medal_stalingrad));
        medals.add(new Medal("Битва за Курск","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Курск", R.drawable.ic_medal_kursk));
    }

    public Medal getMedalById(int id){
        return medals.get(id);
    }
}
