package ru.hld.rustitems.utils;

public class Utils {
    public static Object convertString(String text) {
        Object res = text;

        if("null".equalsIgnoreCase(text)) return null;

        if(text.equalsIgnoreCase("true") || text.equalsIgnoreCase("false")) return Boolean.parseBoolean(text);

        try {
            Integer val = Integer.parseInt(text);
            res = val;
        } catch (Exception e1) {
            try {
                Float val = Float.parseFloat(text);
                res = val;
            } catch (Exception e2) {}
        }

        return res;
    }
}
