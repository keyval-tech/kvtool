package com.kovizone.tool.verify.processor;


import com.kovizone.tool.spel.SpelUtil;
import com.kovizone.tool.verify.anno.VerifyExpression;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class VerifyExpressionProcessor implements VerifyProcessor<VerifyExpression> {
    @Override
    public void verify(VerifyExpression annotation, String fieldName, Object value) throws VerifyException {
        Map<String, Object> variableMap = new HashMap<>(1);
        variableMap.put("this", value);

        String[] values = annotation.value();
        for (String el : values) {
            Boolean result = SpelUtil.parse(el, variableMap, Boolean.class);
            if (result == null || !result) {
                throw new VerifyException(fieldName, "表达式验证不通过");
            }
        }
    }
}
