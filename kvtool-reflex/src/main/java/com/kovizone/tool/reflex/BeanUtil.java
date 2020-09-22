package com.kovizone.tool.reflex;

import com.kovizone.tool.function.SerialFunction;
import com.kovizone.tool.reflex.exception.ReflexException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class BeanUtil {

    public static <T, R> R getValue(T object, SerialFunction<T, R> func) {
        return (R) getValue(object, FieldUtil.getFieldName(func));
    }

    public static Object getValue(Object object, String fieldName) {
        return getValue(object, FieldUtil.getField(object.getClass(), fieldName));
    }

    public static Object getValue(Object object, Field field) {
        Class<?> clazz = object.getClass();
        Method getMethod = MethodUtil.getGetterMethod(clazz, field);
        if (getMethod == null) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException ex) {
                throw new ReflexException("读取属性值失败：" + field.toString());
            }
        }
        try {
            return getMethod.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException ex) {
                e.printStackTrace();
                throw new ReflexException("读取属性值失败：" + field.toString());
            }
        }
    }

    public static <T, R> void setValue(T object, SerialFunction<T, R> func, Object value) {
        setValue(object, FieldUtil.getFieldName(func), value);
    }

    public static void setValue(Object object, String fieldName, Object value) {
        setValue(object, FieldUtil.getField(object.getClass(), fieldName), value);
    }

    public static void setValue(Object object, Field field, Object value) {
        Class<?> clazz = object.getClass();
        Method setMethod = MethodUtil.getSetterMethod(clazz, field);
        if (setMethod == null) {
            field.setAccessible(true);
            try {
                field.set(object, value);
            } catch (IllegalAccessException ex) {
                throw new ReflexException("设置值失败：" + field.toString());
            }
        } else {
            try {
                setMethod.invoke(object, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                field.setAccessible(true);
                try {
                    field.set(object, value);
                } catch (IllegalAccessException ex) {
                    e.printStackTrace();
                    throw new ReflexException("设置值失败：" + field.toString());
                }
            }
        }
    }


    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, new Object[0]);
    }

    /**
     * 实例化
     *
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz, Object[] params) {
        if (params == null) {
            params = new Object[0];
        }
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == params.length) {
                try {
                    Object entity = constructor.newInstance(params);
                    return (T) entity;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
                }
            }
        }
        throw new ReflexException("没有找到合适的构造方法：" + clazz.toString());
    }

    public static <T> T newInstance(Object original, Class<T> clazz) {
        return newInstance(original, clazz, new Object[0]);
    }

    public static <T> T newInstance(Object original, Class<T> clazz, Object[] params) {
        Class<?> originalClass = original.getClass();
        T t = newInstance(clazz, params);
        Field[] originalFields = ClassUtil.getDeclaredFields(originalClass);
        for (Field originalField : originalFields) {
            String originalFieldName = originalField.getName();
            try {
                Object originalFieldValue = BeanUtil.getValue(original, originalField);
                Field field = ClassUtil.getDeclaredField(clazz, originalFieldName);
                BeanUtil.setValue(t, field, originalFieldValue);
            } catch (ReflexException ignored) {
            }
        }
        return t;
    }

    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz) {
        return newInstanceList(originalCollection, clazz, null, null);
    }

    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz, Object[] params) {
        return newInstanceList(originalCollection, clazz, params, null);
    }

    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz, Consumer<T> done) {
        return newInstanceList(originalCollection, clazz, null, done);
    }

    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz, Object[] params, Consumer<T> elementRender) {
        if (originalCollection == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Object object : originalCollection) {
            T t = newInstance(object, clazz, params);
            if (elementRender != null) {
                elementRender.accept(t);
            }
            list.add(t);
        }
        return list;
    }
}
