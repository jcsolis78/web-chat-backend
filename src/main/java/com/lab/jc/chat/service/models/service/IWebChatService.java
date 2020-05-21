package com.lab.jc.chat.service.models.service;

import com.lab.jc.chat.service.models.documents.Message;

import java.util.List;

public interface IWebChatService {

    List<Message> getLast10Messages();
    Message saveChat(Message message);
}
