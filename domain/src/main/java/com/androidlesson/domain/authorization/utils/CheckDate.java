package com.androidlesson.domain.authorization.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckDate {
    public static boolean isValid(String input) {
        String regex = "^(\\d{4}\\s*-?\\s*\\d{4}|\\d{4}\\s*-?\\s*(н\\.в|нв|н\\s*в))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            if (input.contains("-")) {
                String[] parts = input.split("-");
                try {
                    int firstNumber = Integer.parseInt(parts[0].trim());
                    if (parts[1].contains("н") || parts[1].contains("в")) {
                        return true;
                    }
                    int secondNumber = Integer.parseInt(parts[1].trim());
                    return firstNumber < secondNumber;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
