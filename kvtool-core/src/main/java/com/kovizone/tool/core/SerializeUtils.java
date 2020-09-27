package com.kovizone.tool.core;

import java.io.*;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class SerializeUtils {

    /**
     * 对象序列化为字节数组
     *
     * @param obj 对象
     * @return 字节数组
     * @throws IOException IO异常
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } finally {
            ObjectUtil.close(byteArrayOutputStream, objectOutputStream);
        }
    }

    /**
     * 字节数组反序列化为对象
     *
     * @param bytes 字节数组
     * @return 对象
     */
    public static Object deSerialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object;
            try {
                object = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
            objectInputStream.close();
            byteArrayInputStream.close();
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ObjectUtil.close(objectInputStream, byteArrayInputStream);
        }
    }

}
