package com.uapa.software.utils;

import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    public static List<String> extractUserRoles(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        return jwt.getClaim("cognito:groups").asList(String.class);
    }
}
