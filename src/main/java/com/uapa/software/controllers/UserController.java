package com.uapa.software.controllers;

import com.uapa.software.entities.User;
import com.uapa.software.services.CognitoService;
import com.uapa.software.services.UserService;
import com.uapa.software.utils.CognitoServiceFactory;

public class UserController {

    private static final CognitoService cognitoService = CognitoServiceFactory.createCognitoService();
    private static final UserService userService = new UserService(cognitoService);

    public String login(User value) {
        if (value.getUsername().equals("") || value.getPassword().equals("")) {
            throw new IllegalArgumentException("Username and password must be filled");
        }
        return userService.login(value);
    }

    public String saveAndLogin(User value) {
        if (value.getUsername().equals("") || value.getPassword().equals("") || value.getRol().equals("")) {
            throw new IllegalArgumentException("Every fields must be filled");
        }

        return userService.saveAndLogin(value);
    }
}
