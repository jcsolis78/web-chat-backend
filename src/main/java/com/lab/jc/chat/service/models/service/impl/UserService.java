package com.lab.jc.chat.service.models.service.impl;

import com.lab.jc.chat.service.models.dao.IUserRepository;
import com.lab.jc.chat.service.models.documents.UserChat;
import com.lab.jc.chat.service.models.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;


    @Override
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public UserChat update(String username, UserChat user) throws Exception {
        UserChat findUser = userRepository.findByUsername(username);

        if(findUser == null){
            throw new Exception("El usuario no esta registrado");
        }

        findUser.setStatus(user.getStatus() == null ? false : user.getStatus());
        findUser.setColor(user.getColor());
        findUser.setEmail(user.getEmail());
        findUser.setName(user.getName());

        return userRepository.save(findUser);
    }

    @Override
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public UserChat create(UserChat user) throws Exception {
        System.out.println("Usuario: " + user);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserChat> findAllConnectedUser() {
        return userRepository.findByStatus(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserChat> findAllUsers() {
        return (List<UserChat>)userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserChat findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserChat findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
