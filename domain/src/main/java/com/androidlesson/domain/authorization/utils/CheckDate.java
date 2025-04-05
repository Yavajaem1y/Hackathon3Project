package com.androidlesson.domain.authorization.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckDate {

    // Основной метод для проверки строки
    public static boolean isValid(String input) {
        // Регулярное выражение для проверки ввода
        String regex = "^(\\d{4}\\s*-?\\s*\\d{4}|\\d{4}\\s*-?\\s*(н\\.в|нв|н\\s*в))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // Если строка содержит "-"
            if (input.contains("-")) {
                String[] parts = input.split("-");
                return isValidSecondNumber(parts[1]);
            }
            return true; // если строка без "-"
        }

        return false; // если строка не соответствует шаблону
    }

    // Метод для проверки первого числа, что оно больше 1946
    public static boolean isValidFirstNumber(String input) {
        try {
            int firstNumber = Integer.parseInt(input.trim());
            return firstNumber > 1946;
        } catch (NumberFormatException e) {
            return false; // Если строка не может быть преобразована в число, возвращаем false
        }
    }

    // Метод для проверки второго числа или "нв" / "н.в"
    private static boolean isValidSecondNumber(String input) {
        // Если вторая часть строки - "нв" или "н.в", сразу возвращаем true
        if (input.contains("н") || input.contains("в")) {
            return true;
        }

        try {
            int secondNumber = Integer.parseInt(input.trim());

            // Проверяем, что второе число не больше 2025
            return secondNumber <= 2025;
        } catch (NumberFormatException e) {
            return false; // Если не удается преобразовать в число, возвращаем false
        }
    }
}
