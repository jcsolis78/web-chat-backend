package com.lab.jc.chat.service.controllers;

import com.lab.jc.chat.service.models.documents.Message;
import com.lab.jc.chat.service.models.documents.UserChat;
import com.lab.jc.chat.service.models.service.IUserService;
import com.lab.jc.chat.service.models.service.IWebChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Random;

@Controller
public class ChatController {

    private String[] colores = {"red", "yellow", "blue", "green", "magenta", "purple", "orange", "black", "brown", "lightblue"};


    @Autowired
    private IWebChatService chatService;

    @Autowired
    private IUserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/message")
    @SendTo("/chat/message")
    public Message receiveMessage(Message message) throws Exception {

        message.setDate(new Date().getTime());
        UserChat userChat = null;

        String color = "";

        switch (message.getTypeMessage()){
            case "LEAVE":
                 userChat = userService.findUserByUsername(message.getUsername());
                 userChat.setStatus(false);
                 userService.create(userChat);

                break;
            case "NEW_USER":
                color = colores[new Random().nextInt(colores.length)];

                 userChat = userService.findUserByUsername(message.getUsername());

                 userChat.setColor(color);

                 userService.create(userChat);

                message.setText("New user");
                break;
        }

        message.setColor(color);
        chatService.saveChat(message);

        return message;
    }

    @MessageMapping("/writing")
    @SendTo("/chat/writing")
    public String isWriting(String username){
        return username.concat(" is writing... ");
    }

    @MessageMapping("/history")
    public void historyChat(String clientId){
        messagingTemplate.convertAndSend("/chat/history" + clientId, chatService.getLast10Messages());
    }

    @MessageMapping("/users")
    public void usersChat(){
        messagingTemplate.convertAndSend("/chat/users" , userService.findAllConnectedUser());
    }




}
