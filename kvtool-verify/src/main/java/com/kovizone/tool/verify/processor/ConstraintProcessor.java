package com.kovizone.tool.verify.processor;

import com.kovizone.tool.verify.anno.Constraint;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

import java.util.regex.Pattern;

/**
 * Constraint处理类
 *
 * @author KoviChen
 */
public class ConstraintProcessor implements VerifyProcessor<Constraint> {

    private final static Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    private final static Pattern ENGLISH_PATTERN = Pattern.compile("[a-zA-Z]");
    private final static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");
    private final static Pattern SPECIAL_SYMBOLS_PATTERN = Pattern.compile("[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");

    @Override
    public void verify(Constraint constraint, String fieldName, Object value) throws VerifyException {
        String valueStr = String.valueOf(value);

        if (!constraint.chinese()) {
            if (CHINESE_PATTERN.matcher(valueStr).find()) {
                throw new VerifyException(fieldName, "不允许出现中文");
            }
        }
        if (!constraint.english()) {
            if (ENGLISH_PATTERN.matcher(valueStr).find()) {
                throw new VerifyException(fieldName, "不允许出现英文");
            }
        }
        if (!constraint.number()) {
            if (NUMBER_PATTERN.matcher(valueStr).find()) {
                throw new VerifyException(fieldName, "不允许出现数字");
            }
        }
        if (!constraint.specialSymbols()) {
            if (SPECIAL_SYMBOLS_PATTERN.matcher(valueStr).find()) {
                throw new VerifyException(fieldName, "不允许出现特殊符号");
            }
        }
        if (!"".equals(constraint.RegExp())) {
            if (Pattern.compile(constraint.RegExp()).matcher(valueStr).find()) {
                throw new VerifyException(fieldName, "格式不符合正则表达式：{}", constraint.RegExp());
            }
        }
    }
}
