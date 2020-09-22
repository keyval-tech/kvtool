package com.kovizone.tool.verify.processor;


import com.kovizone.tool.core.ObjectUtil;
import com.kovizone.tool.verify.anno.Option;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

import java.util.Arrays;
import java.util.List;

/**
 * Option处理类
 *
 * @author KoviChen
 */
public class OptionProcessor implements VerifyProcessor<Option> {
    @Override
    public void verify(Option option, String fieldName, Object value) throws VerifyException {
        if (!ObjectUtil.isEmpty(value)) {
            List<String> options = Arrays.asList(option.value());
            if (!options.contains((String) value)) {
                throw new VerifyException(fieldName, "取值范围：{}", Arrays.toString(option.value()));
            }
        }
    }
}
