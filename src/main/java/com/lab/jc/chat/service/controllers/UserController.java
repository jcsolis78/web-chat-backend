package com.lab.jc.chat.service.controllers;

import com.lab.jc.chat.service.models.documents.UserChat;
import com.lab.jc.chat.service.models.service.IUploadFileService;
import com.lab.jc.chat.service.models.service.IUserService;
import com.lab.jc.chat.service.models.service.impl.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"https://jcso-web-chat.web.app", "http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUploadFileService fileService;

    @Autowired
    private OauthService oauthService;

    @GetMapping("/user")
    public List<UserChat> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/user/{username}")
    public UserChat findByUsername(@PathVariable String username){
        return userService.findUserByUsername(username);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserChat create(@RequestBody UserChat user) throws Exception {

        user.setStatus(false);
        user.setColor("");

        return userService.create(user);
    }

    @PutMapping("/user/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserChat update(@PathVariable String username,@RequestBody UserChat user) throws Exception {
        return userService.update(username, user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){

        fileService.delete(userService.findById(id).getPicture());
        userService.delete(id);
    }

    @PostMapping("/user/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        UserChat user = userService.findUserByUsername(username);

        if(!file.isEmpty()){
            String fileName = null;
            try {
                fileName = fileService.save(file);
            } catch (IOException e) {
                response.put("message", "Error, upload file ");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            fileService.delete(user.getPicture());

            user.setPicture(fileName);

            userService.create(user);

        }

        return new ResponseEntity< Map<String, Object> >(response, HttpStatus.CREATED);
    }

    @GetMapping("/upload/img/{fileName:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String fileName)  {

        Resource resource = null;

        try {
            resource = fileService.upload(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource,httpHeaders,  HttpStatus.OK);
    }

    @PostMapping("/user/oauth")
    public ResponseEntity<?> oauth(@RequestBody UserChat user) throws Exception {
        Map<String, Object> response = new HashMap<>();

        if(oauthService.validate(user)){
            response.put("message", "Access Granted!");
            response.put("user", userService.findUserByUsername(user.getUsername()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            response.put("message", "Bad Request");
            response.put("error", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }


}
