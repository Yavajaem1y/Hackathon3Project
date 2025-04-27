package com.androidlesson.hackathon3project.presentation.main.models;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;

import java.util.ArrayList;
import java.util.List;

public class MedalsHolder {
    private List<Medal> medals=new ArrayList<>();

    public MedalsHolder() {
        medals.add(new Medal("Медаль за начало","Поздравляем вы получили медаль за прохождение начального раздела", R.drawable.ic_start_medal,"За изучение первых событий Великой Отечественной войны — времени тяжелых испытаний и первых героических сражений"));
        medals.add(new Medal("Битва за Москву","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Москву", R.drawable.ic_moscow_medal,"За изучение героической обороны Москвы — ключевого сражения, ставшего поворотным моментом войны"));
        medals.add(new Medal("Битва за Ленинград","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Ленинград", R.drawable.ic_medal_leningrad,"За изучение подвига защитников Ленинграда, которая стала примером несгибаемой силы духа и мужества"));
        medals.add(new Medal("Битва за Сталинград","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Сталинград", R.drawable.ic_medal_stalingrad,"За изучение героической обороны Сталинграда — сражения, ставшего символом стойкости и переломным моментом Великой Отечественной войны"));
        medals.add(new Medal("Битва за Курск","Поздравляем вы получили медаль за прохождение раздела посвященного битве за Курск", R.drawable.ic_medal_kursk,"За изучение битвы под Курском — крупнейшего танкового сражения в истории, завершившего стратегическую инициативу врага"));
        medals.add(new Medal("Освобождение Крыма","???", R.drawable.ic_medal_lock,"???"));
        medals.add(new Medal("Белорусская операция","???", R.drawable.ic_medal_lock,"???"));
        medals.add(new Medal("Штурм Берлина","???", R.drawable.ic_medal_lock,"???"));
    }

    public Medal getMedalById(int id){
        return medals.get(id);
    }

    public List<Medal> getMedals() {
        return medals;
    }
}
