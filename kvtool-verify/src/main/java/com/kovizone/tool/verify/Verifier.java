package com.kovizone.tool.verify;

import com.kovizone.tool.core.ArrayUtil;
import com.kovizone.tool.core.ObjectUtil;
import com.kovizone.tool.reflex.BeanUtil;
import com.kovizone.tool.reflex.ClassUtil;
import com.kovizone.tool.verify.anno.Alias;
import com.kovizone.tool.verify.anno.Nullable;
import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.api.processor.VerifyProcessor;
import com.kovizone.tool.verify.exception.VerifyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 验证工具类
 *
 * @author KoviChen
 * @version 0.0.1 20191215 KoviChen 新建类
 */
public class Verifier {

    public static void verifyObject(Object check) throws VerifyException {
        verifyObject(check, null);
    }

    public static void verifyObject(Object check, String fieldNamePrefix) throws VerifyException {
        if (fieldNamePrefix == null) {
            fieldNamePrefix = "";
        }
        if (check == null) {
            String fieldName =
                    fieldNamePrefix.length() == 0
                            ? "unKnow"
                            : fieldNamePrefix.concat(".").concat("unKnow");
            throw new VerifyException(fieldName, "对象为空");
        }
        Class<?> clazz = check.getClass();
        Field[] fields = ClassUtil.getDeclaredFields(clazz);
        Annotation[] classAnnotations = ClassUtil.getDeclaredAnnotations(clazz);

        if (fields != null) {
            for (Field field : fields) {
                final Object value = BeanUtil.getValue(check, field);
                checkNull(fieldNamePrefix, field, value);

                Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                Annotation[] annotations = ArrayUtil.addAll(classAnnotations, fieldAnnotations);
                String fieldName = fieldName(field);
                fieldName = fieldNamePrefix.length() == 0 ? fieldName : fieldNamePrefix.concat(".").concat(fieldName);
                for (Annotation annotation : annotations) {
                    VerifyProcessor verifyProcessor = processor(annotation);
                    if (verifyProcessor != null) {
                        verifyProcessor.verify(annotation, fieldName, value);
                    }
                }
            }
        }
    }

    private static void checkNull(String fieldNamePrefix, Field field, Object value) throws VerifyException {
        if (ObjectUtil.isEmpty(value) && !field.isAnnotationPresent(Nullable.class)) {
            String fieldName = fieldName(field);
            fieldName = fieldNamePrefix.length() == 0 ? fieldName : fieldNamePrefix.concat(".").concat(fieldName);
            throw new VerifyException(fieldName, "不允许为空");
        }
    }

    private static String fieldName(Field field) {
        String fieldName;
        if (field.isAnnotationPresent(Alias.class)) {
            fieldName = field.getDeclaredAnnotation(Alias.class).value();
        } else {
            fieldName = field.getName();
        }
        return fieldName;
    }

    private static <P> P processor(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        if (annotationClass.isAnnotationPresent(Processor.class)) {
            Processor processor = annotationClass.getAnnotation(Processor.class);
            Class<?> clazz = processor.value();
            return (P) BeanUtil.newInstance(clazz);
        }
        return null;
    }
}
