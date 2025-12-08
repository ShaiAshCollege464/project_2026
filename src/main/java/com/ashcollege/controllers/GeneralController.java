package com.ashcollege.controllers;

import com.ashcollege.entities.*;
import com.ashcollege.responses.*;
import com.ashcollege.service.Persist;
import com.ashcollege.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

import java.util.List;

import static com.ashcollege.utils.Constants.USER_TYPE_CLIENT;
import static com.ashcollege.utils.Errors.*;

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
    
    @RequestMapping("/get-user-posts")
    public BasicResponse getUserPosts(String token) {
        ClientEntity clientEntity = persist.getClientByToken(token);
        if (clientEntity != null) {
            List<PostEntity> posts = persist.getPostsByClientId(clientEntity.getId()).stream().filter(post -> !post.isDeleted()).toList();
            return new ClientPostsResponse(true, null, posts);
        } else {
            return new BasicResponse(false, ERROR_WRONG_CREDENTIALS);
        }
    }

    @RequestMapping("/get-all-posts")
    public BasicResponse getAllPosts(String token) {
        ProffesionalEntity proffesionalEntity = persist.getProfessionalByToken(token);
        if (proffesionalEntity != null) {
            List<PostEntity> posts = persist.getAllPost().stream().filter(post -> !post.isDeleted()).toList();
            return new ProffesionalPostsResponse(true, null, posts);
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

    @RequestMapping("/delete-post")
    public BasicResponse deletePost (String token, int postId) {
        ClientEntity clientEntity = persist.getClientByToken(token);
        if (clientEntity != null) {
            PostEntity postEntity = persist.getPostByPostId(postId);
            if (clientEntity.getId()==postEntity.getClientEntity().getId()) {
                postEntity.setDeleted(true);
                persist.save(postEntity);
                return new BasicResponse(true, null);
            }else {
                return new BasicResponse(false, ERROR_NOT_AUTHORIZED);
            }
        } else {
            return new BasicResponse(false,ERROR_WRONG_CREDENTIALS);
        }
    }





}
