package com.leo.checkin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leo.checkin.autoconfigure.TokenProperties;
import com.leo.checkin.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Description: TokenUtil
 * @Author: leo
 * @Date: 2021/9/16 21:43
 */
@Component
public class TokenUtil {
    private static TokenProperties tokenProperties;
    @Autowired
    private TokenProperties properties;

    @PostConstruct
    public void init() {
        tokenProperties = properties;
    }
    /**
     * 获取token
     * @param username
     * @return
     */
    public static String getToken(String username) {
        return JWT.create()
                  .withClaim("name", username)
                  .withExpiresAt(new Date(System.currentTimeMillis() + tokenProperties.getExpire()))
                  .sign(Algorithm.HMAC256(tokenProperties.getSecret()));
    }

    /**
     *
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenProperties.getSecret())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException("token 过期或者非法");
        }
        return jwt.getClaim("name").as(String.class);
    }

}
