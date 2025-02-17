package com.uapa.software.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uapa.software.entities.User;

public class UserService {

    private final CognitoService cognitoService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    public String login(User entity) {
        String token = cognitoService.authenticate(entity.getUsername(), entity.getPassword());
        if (token != null) {
            logger.info("User logged in: {}", entity.getUsername());
            return token;
        }
        return null;
    }

    public String saveAndLogin(User entity) {
        cognitoService.registerUser(entity.getUsername(), entity.getPassword());
        String token = cognitoService.authenticate(entity.getUsername(), entity.getPassword());

        if (token != null) {
            logger.info("User logged in: {}", entity.getUsername());
            return token;
        }
        return null;
    }
}
