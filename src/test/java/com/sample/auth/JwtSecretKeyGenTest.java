package com.sample.auth;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtSecretKeyGenTest {
    @Test
    public void generateKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String secret = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println(secret);
    }
}
