package ru.hld.rustitems.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static String removeColorCodes(String text) {
        return removeHexCode(text.replaceAll("§.", ""));
    }

    public static String removeHexCode(String text) {
        return Pattern.compile("#[a-fA-F0-9]{6}").matcher(text).replaceAll("");
    }
}
