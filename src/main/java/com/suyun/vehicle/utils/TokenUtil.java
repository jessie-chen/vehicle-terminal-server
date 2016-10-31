package com.suyun.vehicle.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * Created by IT on 16/10/13.
 */
@Component
public class TokenUtil {

    @Value("${secret.key}")
    private String secret;

    public String generateToken(String content) throws Exception {
        return DigestUtil.encodeBase64(content)+"."+ DigestUtil.sign(content, secret);
    }

    public boolean verifyToken(String token) throws Exception {
        String[] s = token.split("\\.");
        return DigestUtil.sign(new String(DigestUtil.decodeBase64(s[0])), secret).equals(s[1]);
    }

    public String extractToken(String token) throws Exception {
        String[] s = token.split("\\.");
        String src = new String(DigestUtil.decodeBase64(s[0]));
        if(DigestUtil.sign(src, secret).equals(s[1])) { // valid
            return src;
        } else { // invalid
            return null;
        }
    }
}
