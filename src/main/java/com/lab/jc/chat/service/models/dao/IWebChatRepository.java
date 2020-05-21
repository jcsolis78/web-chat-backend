package com.lab.jc.chat.service.models.dao;


import com.lab.jc.chat.service.models.documents.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWebChatRepository extends CrudRepository<Message, Long> {
    List<Message> findFirst10ByOrderByDateDesc();
}
