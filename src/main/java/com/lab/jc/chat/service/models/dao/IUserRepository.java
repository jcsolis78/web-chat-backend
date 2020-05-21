package com.lab.jc.chat.service.models.dao;

import com.lab.jc.chat.service.models.documents.UserChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<UserChat, Long> {
    List<UserChat> findByStatus(Boolean status);
    UserChat findByUsername(String username);
}
