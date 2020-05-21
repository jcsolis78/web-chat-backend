package com.lab.jc.chat.service.models.service;

import com.lab.jc.chat.service.models.documents.UserChat;

import java.util.List;

public interface IUserService {

    UserChat update(String username, UserChat user) throws Exception;
    UserChat create(UserChat user) throws Exception;
    List<UserChat> findAllConnectedUser();
    List<UserChat> findAllUsers();
    UserChat findUserByUsername(String username);
    UserChat findById(Long id);
    void delete(Long id);


}
