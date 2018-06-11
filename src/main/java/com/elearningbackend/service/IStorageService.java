package com.elearningbackend.service;

import com.elearningbackend.customexception.CustomStorageException;
import com.elearningbackend.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Created by dohalong on 11/12/2017.
 */
public interface IStorageService {
    Path initFolder(String key) throws CustomStorageException;
    FileDto store(MultipartFile file, String username) throws CustomStorageException;
    FileDto loadFile(String fileUrl) throws CustomStorageException;
    void deleteFile(String fileUrl) throws CustomStorageException;
    String generateFileUrl(String folderName, String fileName) throws CustomStorageException;
}
