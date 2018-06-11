package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.CustomStorageException;
import com.elearningbackend.dto.FileDto;
import com.elearningbackend.utility.CodeGenerator;
import com.elearningbackend.utility.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

/**
 * Created by dohalong on 11/12/2017.
 */
@Service
@PropertySource("classpath:/storage.properties")
public class UserStorageService implements IStorageService {
    @Value("${path.root.user}")
    private String userRootLocation;

    @Value("${path.root.images}")
    private String imageRootLocation;

    @Value("${url.user.anonymous_picture}")
    private String anonymousPictureUrl;
    private String generatedUrl;

    @Override
    public Path initFolder(String key) throws CustomStorageException {
        Path userProfileImagePath = getUserProfileImagePath(key);
        if(!Files.exists(userProfileImagePath)){
            try {
                return Files.createDirectory(getUserProfileImagePath(key),
                    PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
            } catch (IOException e) {
                throw new CustomStorageException(Errors.CANNOT_CREATE_USER_FOLDER.getId(), Errors.CANNOT_CREATE_USER_FOLDER.getAdditionalMessage(e.getMessage()));
            }
        }
        return userProfileImagePath;
    }

    @Override
    public FileDto store(MultipartFile file, String username) throws CustomStorageException {
        if (!isImageFileValid(file.getContentType())){
            throw new CustomStorageException(Errors.IMAGE_FILE_NOT_SUPPORTED.getId(), Errors.IMAGE_FILE_NOT_SUPPORTED.getMessage());
        }
        isImageFileCorrupted(file);
        if(isImageSizeValid(file.getSize())) {
            try {
                Path initFolder = initFolder(username);
                String originalFilenameTrim = file.getOriginalFilename().trim();
                generatedUrl = generateFileUrl(initFolder.getFileName().toString(), originalFilenameTrim);
                Path originalFilePath = initFolder.resolve(originalFilenameTrim);
                Files.copy(file.getInputStream(), originalFilePath);
                Files.move(originalFilePath, Paths.get(getFullPathByFileUrl(generatedUrl)));
                return FileDto.builder()
                    .originalFileName(originalFilenameTrim)
                    .contentType(file.getContentType())
                    .url(generatedUrl)
                    .size(file.getSize())
                    .build();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomStorageException(Errors.CANNOT_STORE_FILE.getId(),
                        Errors.CANNOT_STORE_FILE.getAdditionalMessage(e.getClass().getName()));
            }
        }
        return null;
    }

    @Override
    public FileDto loadFile(String fileUrl) throws CustomStorageException {
        try {
            File file = new File(getFullPathByFileUrl(fileUrl));
            return FileDto.builder().originalFileName(file.getName())
                .contentType(Files.probeContentType(file.toPath()))
                .url(fileUrl).size(file.length()).build();
        } catch (IOException e) {
            throw new CustomStorageException(Errors.CANNOT_GET_FILE.getId(), Errors.CANNOT_GET_FILE.getAdditionalMessage(e.getMessage()));
        }
    }

    @Override
    public void deleteFile(String fileUrl) throws CustomStorageException {
        if (!fileUrl.equals(anonymousPictureUrl) && !fileUrl.equals(Constants.NA)) {
            try {
                Files.delete(Paths.get(getFullPathByFileUrl(fileUrl)));
            } catch (IOException e) {
                throw new CustomStorageException(Errors.CANNOT_DELETE_FILE.getId(), Errors.CANNOT_DELETE_FILE.getAdditionalMessage(e.getMessage()));
            }
        }
    }

    @Override
    public String generateFileUrl(String folderName, String fileName) throws CustomStorageException {
        return "user_profile/"+folderName+"/"+CodeGenerator.generateFileUrl(fileName);
    }

    private Path getUserProfileImagePath(String username) {
        return Paths.get(userRootLocation+"/"+username);
    }

    private boolean isImageFileValid(String contentType){
        return Constants.VALID_IMAGE_TYPE.stream().anyMatch(e -> e.equalsIgnoreCase(contentType));
    }

    private boolean isImageSizeValid(long size) throws CustomStorageException{
        if (size <= 0) {
            throw new CustomStorageException(Errors.IMAGE_FILE_EMPTY.getId(), Errors.IMAGE_FILE_EMPTY.getMessage());
        }
        if (size > Constants.AVATAR_MAXIMUM_SIZE) {
            throw new CustomStorageException(Errors.IMAGE_FILE_TOO_LARGE.getId(), Errors.IMAGE_FILE_TOO_LARGE.getMessage());
        }
        return true;
    }

    private String getFullPathByFileUrl(String fileUrl) {
        return imageRootLocation+"/"+fileUrl;
    }

    private void isImageFileCorrupted(MultipartFile file) throws CustomStorageException{
        try (InputStream input = file.getInputStream()) {
            try {
                ImageIO.read(input).toString();
            } catch (Exception e) {
                throw new CustomStorageException(Errors.IMAGE_FILE_CORRUPTED.getId(), Errors.IMAGE_FILE_CORRUPTED.getMessage());
            }
        } catch (IOException e) {
            throw new CustomStorageException(Errors.IMAGE_FILE_CORRUPTED.getId(), Errors.IMAGE_FILE_CORRUPTED.getMessage());
        }
    }
}
