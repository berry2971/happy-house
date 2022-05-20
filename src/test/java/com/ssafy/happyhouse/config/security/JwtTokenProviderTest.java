package com.ssafy.happyhouse.config.security;

import com.ssafy.happyhouse.domain.entity.EnumRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static String SECRET_KEY = "aVqX0L3x5iy64ivr5W0jAuReApaYZJh2Xo6U";
    private static final String ISSUER = "com.ssafy.happyhouse";
    private static final int TOKEN_VALID_HOURS = 24 * 14;

    static {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void printTokenForTest() {
        String token = jwtTokenProvider.createToken("ssafy");
        System.out.println(token);
    }

    @Test
    void printLengthOfEncodedPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("asdkfkej").length());
        System.out.println(passwordEncoder.encode("asdv3z").length());
        System.out.println(passwordEncoder.encode("baazakjdjfk321fsgjklajakldfz").length());
    }

    @Test
    void init() {
    }

    @Test
    void createToken() {
    }

    @Test
    void createExpiredToken() {
    }

    @Test
    void verify() {
    }

    @Test
    void getIdFromToken() {
    }
}