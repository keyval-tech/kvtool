package com.kovizone.tool.verify.processor;


import com.kovizone.tool.verify.anno.NumString;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

/**
 * NumString处理类
 *
 * @author KoviChen
 */
public class NumStringProcessor implements VerifyProcessor<NumString> {

    private final static String POINT = ".";

    @Override
    public void verify(NumString numString, String fieldName, Object value) throws VerifyException {
        String valueString = String.valueOf(value);
        if (!numString.point() && valueString.contains(POINT)) {
            throw new VerifyException(fieldName, "不允许出现小数");
        }
        double number;
        try {
            number = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            throw new VerifyException(fieldName, "只允许为数字");
        }

        if (!numString.symbol() && number < 0) {
            throw new VerifyException(fieldName, "只允许为正数");
        }
    }
}
