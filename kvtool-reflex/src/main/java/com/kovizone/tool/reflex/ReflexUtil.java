package com.kovizone.tool.reflex;

import com.kovizone.tool.core.ArrayUtil;
import com.kovizone.tool.core.StringUtil;
import com.kovizone.tool.core.function.SerialFunction;
import com.kovizone.tool.reflex.exception.ReflexException;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ReflexUtil {

    private final static Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new HashMap<>();

    private final static Map<Class<?>, Annotation[]> DECLARED_ANNOTATIONS_CACHE = new HashMap<>();

    private final static int GET_SUPPER_CLASS_MAX_LOOP = 5;

    private final static String FIELD_GET_METHOD_PREFIX = "get";

    private final static String FIELD_IS_METHOD_PREFIX = "is";

    private final static String FIELD_SET_METHOD_PREFIX = "set";

    /**
     * 读取类树集
     *
     * @param clazz 类
     * @return 类树集
     */
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

    /**
     * 读取属性
     *
     * @param clazz     类
     * @param fieldName 属性名
     * @return 属性
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field[] fields = getDeclaredFields(clazz);
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        throw new ReflexException("找不到属性：" + fieldName);
    }

    /**
     * 读取属性集，含父类的属性
     *
     * @param clazz 类
     * @return 属性集
     */
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

    /**
     * 读取注解集，含父类的注解
     *
     * @param clazz 类
     * @return 注解集
     */
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

    /**
     * 读取clazz的父类引用的泛型
     *
     * @param clazz 父类存在泛型的clazz
     */
    public static Class<?>[] getSuperClassGenericTypes(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        Class<?>[] classes = new Class<?>[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class<?>) types[i];
        }
        return classes;
    }

    public static Method getGetterMethod(Class<?> clazz, Field field) {
        Method getMethod = null;
        // 布偶类型且有is前缀的属性
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            if (field.getName().startsWith(FIELD_IS_METHOD_PREFIX)) {
                getMethod = getMethod(clazz, field.getName());
                if (getMethod == null) {
                    String methodName = FIELD_GET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName().substring(FIELD_IS_METHOD_PREFIX.length())));
                    getMethod = getMethod(clazz, methodName);
                }
            }
            if (getMethod == null) {
                String methodName = FIELD_IS_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
                getMethod = getMethod(clazz, methodName);
            }
        }
        if (getMethod == null) {
            String methodName = FIELD_GET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
            getMethod = getMethod(clazz, methodName);
        }
        return getMethod;
    }

    public static Method getSetterMethod(Class<?> clazz, Field field) {
        Method setMethod = null;
        // 布偶类型且有is前缀的属性
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            if (field.getName().startsWith(FIELD_IS_METHOD_PREFIX)) {
                String methodName = FIELD_SET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName().substring(FIELD_IS_METHOD_PREFIX.length())));
                setMethod = getMethod(clazz, methodName, field.getType());
            }
        }
        if (setMethod == null) {
            String methodName = FIELD_SET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
            setMethod = getMethod(clazz, methodName, field.getType());
        }
        return setMethod;
    }

    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ignored) {
        }
        return method;
    }

    protected static String getFieldNameParseMethodName(String methodName) {
        String fieldName;
        if (methodName.startsWith(FIELD_GET_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_GET_METHOD_PREFIX.length());
        } else if (methodName.startsWith(FIELD_SET_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_SET_METHOD_PREFIX.length());
        } else if (methodName.startsWith(FIELD_IS_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_IS_METHOD_PREFIX.length());
        } else {
            fieldName = methodName;
        }
        return StringUtil.lowerFirstCase(fieldName);
    }

    /**
     * 读取属性值
     *
     * @param object 对象
     * @param func   属性getter方法
     * @param <T>    对象泛型
     * @param <R>    属性泛型
     * @return 属性值
     */
    public static <T, R> R getFieldValue(T object, SerialFunction<T, R> func) {
        return (R) getFieldValue(object, getFieldName(func));
    }

    /**
     * 读取属性值
     *
     * @param object    对象
     * @param fieldName 属性名
     * @return 属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        return getFieldValue(object, getDeclaredField(object.getClass(), fieldName));
    }

    /**
     * 读取属性值
     *
     * @param object 对象
     * @param field  属性
     * @return 属性值
     */
    public static Object getFieldValue(Object object, Field field) {
        Class<?> clazz = object.getClass();
        Method getMethod = ReflexUtil.getGetterMethod(clazz, field);
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

    /**
     * 设置属性值
     *
     * @param object 对象
     * @param func   属性getter方法
     * @param value  属性值
     * @param <T>    对象泛型
     * @param <R>    属性泛型
     */
    public static <T, R> void setFieldValue(T object, SerialFunction<T, R> func, Object value) {
        setFieldValue(object, getFieldName(func), value);
    }

    /**
     * 设置属性值
     *
     * @param object    对象
     * @param fieldName 属性名
     * @param value     属性值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        setFieldValue(object, getDeclaredField(object.getClass(), fieldName), value);
    }

    /**
     * 设置属性值
     *
     * @param object 对象
     * @param field  属性
     * @param value  属性值
     */
    public static void setFieldValue(Object object, Field field, Object value) {
        Class<?> clazz = object.getClass();
        Method setMethod = ReflexUtil.getSetterMethod(clazz, field);
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

    /**
     * 实例化
     *
     * @param clazz 类
     * @param <T>   类泛型
     * @return 类实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, new Object[0]);
    }

    /**
     * 实例化
     *
     * @param clazz  类
     * @param params 实例化属性
     * @param <T>    类泛型
     * @return 类实例
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

    /**
     * 实例化
     *
     * @param original 数据源，相同属性名时复制值
     * @param clazz    类
     * @param <T>      类泛型
     * @return 类实例
     */
    public static <T> T newInstance(Object original, Class<T> clazz) {
        return newInstance(original, clazz, new Object[0]);
    }

    /**
     * 实例化
     *
     * @param original 数据源，相同属性名时复制值
     * @param clazz    类
     * @param params   实例化属性
     * @param <T>      类泛型
     * @return 类实例
     */
    public static <T> T newInstance(Object original, Class<T> clazz, Object[] params) {
        Class<?> originalClass = original.getClass();
        T t = newInstance(clazz, params);
        Field[] originalFields = ReflexUtil.getDeclaredFields(originalClass);
        for (Field originalField : originalFields) {
            String originalFieldName = originalField.getName();
            try {
                Object originalFieldValue = getFieldValue(original, originalField);
                Field field = ReflexUtil.getDeclaredField(clazz, originalFieldName);
                setFieldValue(t, field, originalFieldValue);
            } catch (ReflexException ignored) {
            }
        }
        return t;
    }

    /**
     * 实例化集
     *
     * @param originalCollection 数据源集，相同属性名时复制值
     * @param clazz              类
     * @param <T>                类泛型
     * @return 类实例集
     */
    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz) {
        return newInstanceList(originalCollection, clazz, null, null);
    }

    /**
     * 实例化集
     *
     * @param originalCollection 数据源集，相同属性名时复制值
     * @param clazz              类
     * @param params             实例化属性
     * @param <T>                类泛型
     * @return 类实例集
     */
    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz, Object[] params) {
        return newInstanceList(originalCollection, clazz, params, null);
    }

    /**
     * 实例化集
     *
     * @param originalCollection 数据源集，相同属性名时复制值
     * @param clazz              类
     * @param elementRender      元素渲染方法
     * @param <T>                类泛型
     * @return 类实例集
     */
    public static <T> List<T> newInstanceList(Collection<?> originalCollection, Class<T> clazz, Consumer<T> elementRender) {
        return newInstanceList(originalCollection, clazz, null, elementRender);
    }

    /**
     * 实例化集
     *
     * @param originalCollection 数据源集，相同属性名时复制值
     * @param clazz              类
     * @param params             实例化属性
     * @param elementRender      元素渲染方法
     * @param <T>                类泛型
     * @return 类实例集
     */
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

    /**
     * 读取属性名
     *
     * @param func 属性方法（getter / setter）
     * @return 属性名
     */
    public static String getFieldName(SerialFunction<?, ?> func) {
        String fieldName;
        try {
            SerializedLambda serializedLambda = serializedLambda(func);
            fieldName = ReflexUtil.getFieldNameParseMethodName(serializedLambda.getImplMethodName());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new ReflexException(e);
        }
        return fieldName;
    }

    private static <T, R> SerializedLambda serializedLambda(SerialFunction<T, R> func) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 调用writeReplace
        Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        return (SerializedLambda) writeReplace.invoke(func);
    }

}
