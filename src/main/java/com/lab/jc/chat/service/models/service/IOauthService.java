package com.lab.jc.chat.service.models.service;

import com.lab.jc.chat.service.models.documents.UserChat;

public interface IOauthService {

    boolean validate(UserChat user) throws Exception;

}
