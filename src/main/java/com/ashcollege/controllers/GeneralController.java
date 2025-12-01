package com.ashcollege.controllers;

import com.ashcollege.entities.*;
import com.ashcollege.responses.BasicResponse;
import com.ashcollege.responses.ClientPostsResponse;
import com.ashcollege.responses.DefaultParamResponse;
import com.ashcollege.responses.LoginResponse;
import com.ashcollege.service.Persist;
import com.ashcollege.utils.GeneralUtils;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

import java.util.List;

import static com.ashcollege.utils.Constants.USER_TYPE_CLIENT;
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
    public BasicResponse getUser (String username, String password, int selectedType) {
        try {
            if (username != null && password != null) {
                if (selectedType == USER_TYPE_CLIENT) {
                    ClientEntity clientEntity = persist.getUserByUsernameAndPassword(username, password);
                    if (clientEntity != null) {
                        String token = GeneralUtils.hashMd5(username, password);
                        clientEntity.setToken(token);
                        persist.save(clientEntity);
                        return new LoginResponse(true, null, 1, token, clientEntity.getId(), selectedType);
                    } else {
                        return new BasicResponse(false,  ERROR_WRONG_CREDENTIALS);
                    }
                } else {
                    ProffesionalEntity proffesionalEntity = persist.getProffesionalByUsernameAndPassword(username, password);
                    if (proffesionalEntity != null) {
                        String token = GeneralUtils.hashMd5(username, password);
                        proffesionalEntity.setToken(token);
                        persist.save(proffesionalEntity);
                        return new LoginResponse(true, null, 1, token, proffesionalEntity.getId(), selectedType);
                    } else {
                        return new BasicResponse(false,  ERROR_WRONG_CREDENTIALS);
                    }

                }
            } else {
                return new BasicResponse(false, ERROR_MISSING_USERNAME_OR_PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    @RequestMapping("/get-default-params")
    public BasicResponse getDefaultParams (String token) {
        BasicUser basicUser = persist.getUserByToken(token);
        if (basicUser != null) {
            return new DefaultParamResponse(true, null, basicUser);
        } else {
            return new BasicResponse(false, ERROR_WRONG_CREDENTIALS);
        }
    }

    @RequestMapping("/add-post")
    public BasicResponse addPost (String token, String text) {
        ClientEntity clientEntity = persist.getClientByToken(token);
        if (clientEntity != null) {
            PostEntity postEntity = new PostEntity();
            postEntity.setClientEntity(clientEntity);
            postEntity.setText(text);
            persist.save(postEntity);
            return new BasicResponse(true, null);
        } else {
            return new BasicResponse(false, ERROR_WRONG_CREDENTIALS);
        }
    }





}
