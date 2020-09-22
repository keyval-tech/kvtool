package com.kovizone.tool.verify.processor;


import com.kovizone.tool.verify.Verifier;
import com.kovizone.tool.verify.anno.VerifyList;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

import java.util.List;

/**
 * VerifyList处理类
 *
 * @author KoviChen
 */
public class VerifyListProcessor implements VerifyProcessor<VerifyList> {
    @Override
    public void verify(VerifyList annotation, String fieldName, Object value) throws VerifyException {
        if (value instanceof List) {
            List<?> list = (List<?>) value;
            for (int i = 0; i < list.size(); i++) {
                String newFileName = fieldName.concat("[").concat(String.valueOf(i)).concat("]");
                Verifier.verifyObject(list.get(i), newFileName);
            }
        }
    }
}
