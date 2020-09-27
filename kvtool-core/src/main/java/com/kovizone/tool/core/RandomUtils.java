package com.kovizone.tool.core;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class RandomUtils extends Random {

    private static Random random = null;

    public static Random getInstance() {
        try {
            if (random == null) {
                random = SecureRandom.getInstance("SHA1PRNG");
            }
            return random;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
