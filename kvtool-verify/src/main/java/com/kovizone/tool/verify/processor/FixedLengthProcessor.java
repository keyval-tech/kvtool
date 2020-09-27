package com.kovizone.tool.verify.processor;


import com.kovizone.tool.core.ObjectUtil;
import com.kovizone.tool.verify.anno.FixedLength;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

/**
 * FixedLength处理类
 *
 * @author KoviChen
 */
public class FixedLengthProcessor implements VerifyProcessor<FixedLength> {

    @Override
    public void verify(FixedLength annotation, String fieldName, Object value) throws VerifyException {
        if (ObjectUtil.isNotEmpty(value)) {
            int fixedLength = annotation.value();
            if (String.valueOf(value).length() != fixedLength) {
                throw new VerifyException(fieldName, "长度只能是{}", fixedLength);
            }
        }
    }
}
