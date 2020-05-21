package com.lab.jc.chat.service.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {

    Resource upload(String fileName) throws MalformedURLException;
    String save(MultipartFile multipartFile) throws IOException;
    boolean delete(String fileName);
    Path getPath(String fileName);

}
