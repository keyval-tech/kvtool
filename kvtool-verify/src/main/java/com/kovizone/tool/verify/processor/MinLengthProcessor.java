package com.kovizone.tool.verify.processor;

import com.kovizone.tool.core.ObjectUtil;
import com.kovizone.tool.verify.anno.MinLength;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

/**
 * MinLength处理类
 *
 * @author KoviChen
 */
public class MinLengthProcessor implements VerifyProcessor<MinLength> {
    @Override
    public void verify(MinLength minLength, String fieldName, Object value) throws VerifyException {
        if (!ObjectUtil.isEmpty(value)) {
            int min = minLength.value();
            if (min > String.valueOf(value).length()) {
                throw new VerifyException(fieldName, "长度不允许小于{}", min);
            }
        }
    }
}
