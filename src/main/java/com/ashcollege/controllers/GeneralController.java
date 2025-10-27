package com.ashcollege.controllers;

import com.ashcollege.entities.*;
import com.ashcollege.service.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

@RestController
public class GeneralController {
    @Autowired
    private Persist persist;

    @PostConstruct
    public void init() {
    }


    @RequestMapping("/get-username-by-email")
    public String getNameByToken(String email){
        String name = null;
        UserEntity user = this.persist.getUserByEmail(email);
        if (user!=null){
            name = user.getUsername();
        }
        return name;
    }




}
