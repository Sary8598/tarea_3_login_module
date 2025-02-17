package com.uapa.software.services;

import java.util.Arrays;
import java.util.List;

import com.uapa.software.utils.JwtUtil;

public class RBACService {
    private static final List<String> ADMIN_ROLES = List.of("Admin", "SuperUser");
    private static final List<String> USER_ROLES = List.of("User", "Customer");
    private static String environment = System.getProperty("env", "local");

    public static boolean isAdmin(String token) {
        List<String> roles = "local".equals(environment) ? Arrays.asList("Admin") : JwtUtil.extractUserRoles(token);
        return roles != null && roles.stream().anyMatch(ADMIN_ROLES::contains);
    }

    public static boolean isUser(String token) {
        List<String> roles = "local".equals(environment) ? Arrays.asList("User") : JwtUtil.extractUserRoles(token);
        return roles != null && roles.stream().anyMatch(USER_ROLES::contains);
    }
}
