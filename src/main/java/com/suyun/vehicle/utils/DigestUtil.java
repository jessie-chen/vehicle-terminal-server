package com.suyun.vehicle.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by IT on 16/10/13.
 */
public class DigestUtil {

    public static String sign(String src, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec s = new SecretKeySpec(
                secret.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(s);
        return encodeBase64(mac.doFinal(src.getBytes()));
    }

    public static String encodeBase64(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    public static String encodeBase64(String content) {
        return encodeBase64(content.getBytes());
    }

    public static byte[] decodeBase64(String content) {
        return Base64.getDecoder().decode(content);
    }
}
