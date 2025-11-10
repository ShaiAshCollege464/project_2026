package com.ashcollege.controllers;

import com.ashcollege.entities.*;
import com.ashcollege.responses.BasicResponse;
import com.ashcollege.responses.LoginResponse;
import com.ashcollege.service.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

import java.util.List;

import static com.ashcollege.utils.Errors.ERROR_MISSING_USERNAME_OR_PASSWORD;
import static com.ashcollege.utils.Errors.ERROR_WRONG_CREDENTIALS;

@RestController
public class GeneralController {
    @Autowired
    private Persist persist;

    @PostConstruct
    public void init() {
    }


    @RequestMapping ("/login")
    public BasicResponse getUser (String username, String password) {
        if (username != null && password != null) {
            if (username.equals("Shai") && password.equals("1234")) {
                return new LoginResponse(true, null, 1, "MY_TOKEN", 1);
            } else {
                return new BasicResponse(false, ERROR_WRONG_CREDENTIALS);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME_OR_PASSWORD);
        }
    }




}
