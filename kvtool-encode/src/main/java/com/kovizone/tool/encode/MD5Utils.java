package com.kovizone.tool.encode;

import com.kovizone.tool.core.MathUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class MD5Utils {

    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytes);
            return new String(MathUtils.bytes2Hex(digest));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String encode(String arg) {
        if (arg == null) {
            return null;
        }
        return encode(arg.getBytes(StandardCharsets.UTF_8));
    }
}
