package ru.vk.competition.minbenchmark.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RootValidation {
    protected static final int MAX_TABLE_NAME_SIZE = 50;
    protected static final int MAX_SQL_QUERY_SIZE = 120;

    public boolean hasEnglishLettersOnly(String str) {
        if(str == null)
            return false;
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean hasEnglishLettersAndNumbers(String str) {
        if(str == null)
            return false;
        Pattern pattern = Pattern.compile("^[^_0-9][a-zA-Z0-9_]+[^_]$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean hasEnglishLettersAndNumbersAndBracers(String str) {
        if(str == null)
            return false;
        Pattern pattern = Pattern.compile("^[^0-9()][a-zA-Z(0-9)]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
