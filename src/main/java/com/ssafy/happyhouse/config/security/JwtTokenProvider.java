package com.ssafy.happyhouse.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String SECRET_KEY = "aVqX0L3x5iy64ivr5W0jAuReApaYZJh2Xo6U";
    private final String ISSUER = "com.ssafy.happyhouse";
    private final int TOKEN_VALID_HOURS = 24 * 14;

    @PostConstruct
    public void init() {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createToken(String id) {
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(ISSUER);
        builder.withClaim("id", id);
        builder.withExpiresAt(getExpirationDate(true));
        return builder.sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String createExpiredToken(String id) {
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(ISSUER);
        builder.withClaim("id", id);
        builder.withExpiresAt(getExpirationDate(false));
        return builder.sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public void verify(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(ISSUER)
                .build();
        jwtVerifier.verify(token);
    }

    public String getIdFromToken(String token) {
        return decode(token).getClaim("id").asString();
    }

    private DecodedJWT decode(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer(ISSUER)
                .build();
        return jwtVerifier.verify(token);
    }

    private Date getExpirationDate(boolean alive) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (alive) {
            calendar.add(Calendar.HOUR, TOKEN_VALID_HOURS);
        }
        return calendar.getTime();
    }

}
