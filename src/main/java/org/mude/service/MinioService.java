package org.mude.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    void uploadFile(String objectName, String bucketName, MultipartFile file);

    byte[]  downloadFile(String objectName, String bucketName);

    boolean fileExists(String bucketName, String filename);
}
