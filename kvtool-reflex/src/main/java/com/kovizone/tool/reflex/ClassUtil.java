package com.kovizone.tool.reflex;

import com.kovizone.tool.core.ArrayUtil;
import com.kovizone.tool.reflex.exception.ReflexException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ClassUtil {

    private final static Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new HashMap<>();

    private final static Map<Class<?>, Annotation[]> DECLARED_ANNOTATIONS_CACHE = new HashMap<>();

    private final static int GET_SUPPER_CLASS_MAX_LOOP = 5;

    public static List<Class<?>> getClassTree(Class<?> clazz) {
        return getClassTreeRecursion(clazz, GET_SUPPER_CLASS_MAX_LOOP);
    }

    private static List<Class<?>> getClassTreeRecursion(Class<?> clazz, int maxLoop) {
        if (maxLoop <= 0) {
            return new ArrayList<>();
        }
        if (maxLoop > GET_SUPPER_CLASS_MAX_LOOP) {
            maxLoop = GET_SUPPER_CLASS_MAX_LOOP;
        }
        if (clazz == null || clazz.equals(Object.class)) {
            return new ArrayList<>();
        }
        List<Class<?>> classList = new ArrayList<>();
        classList.add(clazz);
        classList.addAll(getClassTreeRecursion(clazz.getSuperclass(), maxLoop - 1));
        return classList;
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field[] fields = getDeclaredFields(clazz);
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        throw new ReflexException("找不到属性：" + fieldName);
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] fields = DECLARED_FIELDS_CACHE.get(clazz);

        if (fields == null) {
            List<Class<?>> clazzList = getClassTree(clazz);
            for (Class<?> c : clazzList) {
                Field[] fields2 = c.getDeclaredFields();
                if (fields == null) {
                    fields = fields2;
                } else {
                    fields = ArrayUtil.addAll(fields, fields2);
                }
            }
            DECLARED_FIELDS_CACHE.put(clazz, fields);
        }
        return fields;
    }

    public static Annotation[] getDeclaredAnnotations(Class<?> clazz) {
        Annotation[] annotations = DECLARED_ANNOTATIONS_CACHE.get(clazz);
        if (annotations == null) {
            annotations = new Annotation[0];
            List<Class<?>> clazzList = getClassTree(clazz);
            for (Class<?> c : clazzList) {
                Annotation[] currentAnnotations = c.getDeclaredAnnotations();
                for (int i = 0; i < currentAnnotations.length; i++) {
                    for (Annotation annotation : annotations) {
                        if (currentAnnotations[i].annotationType().equals(annotation.annotationType())) {
                            currentAnnotations[i] = null;
                            break;
                        }
                    }
                }
                annotations = ArrayUtil.trim(ArrayUtil.addAll(annotations, currentAnnotations));
            }
            DECLARED_ANNOTATIONS_CACHE.put(clazz, annotations);
        }
        return annotations;
    }
}
