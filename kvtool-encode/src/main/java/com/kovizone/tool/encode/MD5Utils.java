package com.kovizone.tool.encode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class MD5Utils {

    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytes);
            char[] chars = new char[32];
            for (int i = 0; i < chars.length; i += 2) {
                byte b = digest[i / 2];
                chars[i] = HEX_CHARS[b >>> 4 & 15];
                chars[i + 1] = HEX_CHARS[b & 15];
            }
            return new String(chars);
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
