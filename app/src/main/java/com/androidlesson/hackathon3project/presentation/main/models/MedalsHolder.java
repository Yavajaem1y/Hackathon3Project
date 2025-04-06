package com.androidlesson.hackathon3project.presentation.main.models;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;

import java.util.ArrayList;
import java.util.List;

public class MedalsHolder {
    private List<Medal> medals=new ArrayList<>();

    public MedalsHolder() {
        medals.add(new Medal("Битва за Москву","Ухтф ты осилил целый раздел ахуеть ну держи тогда медаль как у твоего деда это же равнозначная хуйня на войне подохнуть и потапать кнопки запомнить когда вообще началась война какая-то там ну ты молодчина реально ура", R.drawable.ic_moscow_medal));
    }

    public Medal getMedalById(int id){
        return medals.get(id);
    }
}
