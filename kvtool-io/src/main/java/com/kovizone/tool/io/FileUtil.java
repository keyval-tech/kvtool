package com.kovizone.tool.io;

import com.kovizone.tool.core.ObjectUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class FileUtil {


    /**
     * 读取文件二进制
     *
     * @param inputStream 输入流
     * @return 文件二进制
     */
    public static byte[] getFile(InputStream inputStream) throws IOException {
        byte[] data = new byte[inputStream.available()];
        if (inputStream.read(data) == -1) {
            throw new IOException("读取失败");
        }
        return data;
    }

    /**
     * 生成文件Base64字符串
     *
     * @param filePath 文件路径和文件名
     * @return 文件Base64字符串
     */
    public static byte[] getFileByPath(String filePath) throws IOException {
        InputStream inputStream = null;
        byte[] data;
        try {
            inputStream = new FileInputStream(filePath);
            data = getFile(inputStream);
        } finally {
            ObjectUtil.close(inputStream);
        }
        return data;
    }
}
