package com.kovizone.tool.core;

import com.kovizone.tool.core.constant.CharConstant;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class CharUtil {

    public static boolean isUpperCase(char c) {
        return c >= CharConstant.FIRST_UPPER_LETTER && c <= CharConstant.LAST_UPPER_LETTER;
    }

    public static boolean isLowerCase(char c) {
        return c >= CharConstant.FIRST_LOWER_LETTER && c <= CharConstant.LAST_LOWER_LETTER;
    }
}
