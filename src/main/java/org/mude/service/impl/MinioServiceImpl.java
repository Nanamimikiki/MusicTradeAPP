package org.mude.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import org.mude.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class MinioServiceImpl implements MinioService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void uploadFile(String objectName, String bucketName, MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder().object(objectName)
                    .stream(file.getInputStream(), -1, 10485760).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] downloadFile(String objectName, String bucketName) {
        try (InputStream stream = minioClient
                .getObject(GetObjectArgs.builder()
                        .object(objectName)
                        .build())) {
            return stream.readAllBytes();
        } catch (IOException | InsufficientDataException | InvalidResponseException | ServerException |
                 XmlParserException | NoSuchAlgorithmException | InvalidKeyException | ErrorResponseException |
                 InternalException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean fileExists(String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
//    @Override
//    public byte[] downloadFile(String objectName, String bucketName) {
//        try (InputStream stream = minioClient
//                .getObject(GetObjectArgs.builder()
//                        .object(objectName)
//                        .build())) {
//            return stream.readAllBytes();
//        } catch (IOException | InsufficientDataException | InvalidResponseException | ServerException |
//                 XmlParserException | NoSuchAlgorithmException | InvalidKeyException | ErrorResponseException |
//                 InternalException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }