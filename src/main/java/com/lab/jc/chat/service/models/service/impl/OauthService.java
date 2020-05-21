package com.lab.jc.chat.service.models.service.impl;

import com.lab.jc.chat.service.models.documents.UserChat;
import com.lab.jc.chat.service.models.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OauthService implements IOauthService {

    @Autowired
    private UserService userService;

    @Override
    public boolean validate(UserChat user) throws Exception {

        UserChat findUser = userService.findUserByUsername(user.getUsername());

        if(findUser == null){
            return false;
        }

        if(user.getPassword().equals(findUser.getPassword())){
            findUser.setStatus(true);
            userService.create(findUser);
            return true;
        }else{
            return false;
        }

    }
}
