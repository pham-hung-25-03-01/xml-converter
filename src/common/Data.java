package common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import common.Validator.FormatType;

public class Data {

    public enum Type {
        FROM_FILE,
        FROM_DB,
        FROM_GENERATOR,
        FROM_DEFAULT_VALUES,
        FROM_TEMPLATE
    }

    public static Type determineType(String expressionLanguage) {
        if(Pattern.matches("\\$\\{\\w+\\}", expressionLanguage)){
            return Type.FROM_FILE;
        }
        if(Pattern.matches("\\@\\{\\w+\\}", expressionLanguage)){
            return Type.FROM_DB;
        }
        if(Pattern.matches("\\#\\{\\w+\\}", expressionLanguage)){
            return Type.FROM_GENERATOR;
        }
        if(Pattern.matches("\\*\\{\\w+\\}", expressionLanguage)){
            return Type.FROM_DEFAULT_VALUES;
        }
        return Type.FROM_TEMPLATE;
    }

    public static String extractDataName(String expressionLanguage, Type dataType) {
        String regex = "";
        switch(dataType){
            case FROM_FILE:
                regex = "\\$\\{(.+?)\\}";
                break;
            case FROM_DB:
                regex = "\\@\\{(.+?)\\}";
                break;
            case FROM_GENERATOR:
                regex = "\\#\\{(.+?)\\}";
                break;
            case FROM_DEFAULT_VALUES:
                regex = "\\*\\{(.+?)\\}";
                break;
            default:
                regex = "(.+?)";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expressionLanguage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static List<String> getListExpressionLanguage(String s){
        Pattern pattern = Pattern.compile("[\\$|\\@|\\#|\\*]\\{\\w+\\}");
        Matcher matcher = pattern.matcher(s);
        List<String> matches = matcher.results()
                        .map(matchResult -> matchResult.group())
                        .distinct()
                        .collect(Collectors.toList());
        return matches;
    }

    public static String format(String s, FormatType formatType){
        String regex = "";
        switch(formatType){
            case ADD_INFO:
                regex = "\\w+\\=[^\\s|\\;]+\\;";
                break;
            default:
                return s;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            output.append(matcher.group());
        }
        return output.toString();
    }

}
