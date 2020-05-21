package com.lab.jc.chat.service.models.service.impl;

import com.lab.jc.chat.service.models.service.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService implements IUploadFileService {

    private final Logger log = LoggerFactory.getLogger(UploadFileService.class);

    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final String DEFAULT_PATH = "src/main/resources/static/images";
    private static final String DEFAULT_IMAGE = "no_user.png";


    @Override
    public Resource upload(String fileName) throws MalformedURLException {



        Path pathFile = getPath(fileName);

        Resource resource = new UrlResource(pathFile.toUri());

        if(!resource.exists() && !resource.isReadable()){

            pathFile = Paths.get(DEFAULT_PATH).resolve(DEFAULT_IMAGE).toAbsolutePath();

            resource = new UrlResource(pathFile.toUri());

            log.error("Image could not be loaded: " + fileName);
        }
        return resource;
    }

    @Override
    public String save(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename().replace(" ","");
        Path pathFile = getPath(fileName);

        Files.copy(multipartFile.getInputStream(), pathFile);

        return fileName;
    }

    @Override
    public boolean delete(String fileName) {
        if(fileName != null && fileName.length() > 0){
            Path pathFile = Paths.get("uploads").resolve(fileName).toAbsolutePath();
            File lastPicture = pathFile.toFile();
            if(lastPicture.exists() && lastPicture.canRead()){
                lastPicture.delete();
                return false;
            }
        }
        return true;
    }

    @Override
    public Path getPath(String fileName) {
        return Paths.get(UPLOAD_DIRECTORY).resolve(fileName).toAbsolutePath();
    }
}
