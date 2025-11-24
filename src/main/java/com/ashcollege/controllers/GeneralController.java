package com.ashcollege.controllers;

import com.ashcollege.entities.*;
import com.ashcollege.responses.BasicResponse;
import com.ashcollege.responses.ClientPostsResponse;
import com.ashcollege.responses.LoginResponse;
import com.ashcollege.service.Persist;
import com.ashcollege.utils.GeneralUtils;
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
            ClientEntity clientEntity = persist.getUserByUsernameAndPassword(username, password);
            if (clientEntity != null) {
                String token = GeneralUtils.hashMd5(username, password);
                clientEntity.setToken(token);
                persist.save(clientEntity);
                return new LoginResponse(true, null, 1, token, clientEntity.getId());
            } else {
                return new BasicResponse(false,  ERROR_WRONG_CREDENTIALS);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME_OR_PASSWORD);
        }
    }

    @RequestMapping("/get-posts")
    public BasicResponse getPosts (String token) {
        ClientEntity clientEntity = persist.getClientByToken(token);
        if (clientEntity != null) {
            List<PostEntity> posts = persist.getPostsByClientId(clientEntity.getId());
            return new ClientPostsResponse(true, null, posts);
        } else {
            return new BasicResponse(false, ERROR_WRONG_CREDENTIALS);
        }
    }




}
