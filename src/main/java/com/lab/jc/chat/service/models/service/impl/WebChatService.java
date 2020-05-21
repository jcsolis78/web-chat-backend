package com.lab.jc.chat.service.models.service.impl;

import com.lab.jc.chat.service.models.dao.IWebChatRepository;
import com.lab.jc.chat.service.models.documents.Message;
import com.lab.jc.chat.service.models.service.IWebChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebChatService implements IWebChatService {

    @Autowired
    private IWebChatRepository chatRepository;

    @Override
    public List<Message> getLast10Messages() {
        return chatRepository.findFirst10ByOrderByDateDesc();
    }

    @Override
    public Message saveChat(Message message) {
        return chatRepository.save(message);
    }
}
