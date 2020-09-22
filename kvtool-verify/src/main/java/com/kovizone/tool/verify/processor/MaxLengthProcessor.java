package com.kovizone.tool.verify.processor;

import com.kovizone.tool.core.ObjectUtil;
import com.kovizone.tool.verify.anno.MaxLength;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

/**
 * MaxLength处理类
 *
 * @author KoviChen
 */
public class MaxLengthProcessor implements VerifyProcessor<MaxLength> {
    @Override
    public void verify(MaxLength maxLength, String fieldName, Object value) throws VerifyException {
        if (!ObjectUtil.isEmpty(value)) {
            int max = maxLength.value();
            if (max < String.valueOf(value).length()) {
                throw new VerifyException(fieldName, "长度不允许大于{}", max);
            }
        }
    }
}
