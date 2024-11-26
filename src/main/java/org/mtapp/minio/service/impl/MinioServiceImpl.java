package org.mtapp.minio.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.mtapp.minio.service.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void uploadFile(String bucketName, String objectName, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("File '{}' uploaded to bucket '{}' successfully.", objectName, bucketName);
        } catch (Exception e) {
            log.error("Error uploading file '{}' to bucket '{}': {}", objectName, bucketName, e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }

    @Override
    public byte[] downloadFile(String bucketName, String objectName) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {
            log.info("File '{}' downloaded from bucket '{}' successfully.", objectName, bucketName);
            return stream.readAllBytes();
        } catch (Exception e) {
            log.error("Error downloading file '{}' from bucket '{}': {}", objectName, bucketName, e.getMessage());
            throw new RuntimeException("Error downloading file", e);
        }
    }

    @Override
    public boolean fileExists(String bucketName, String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                return false;
            } else {
                log.error("Error checking existence of file '{}' in bucket '{}': {}", objectName, bucketName, e.getMessage());
                throw new RuntimeException("Error checking file existence", e);
            }
        } catch (Exception e) {
            log.error("Error checking existence of file '{}' in bucket '{}': {}", objectName, bucketName, e.getMessage());
            throw new RuntimeException("Error checking file existence", e);
        }
    }

    @Override
    public String getFileLink(String bucketName, String fileName) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(2)
                            .build()
            );
            log.info("Generated presigned URL for file '{}' in bucket '{}'", fileName, bucketName);
            return url;
        } catch (Exception e) {
            log.error("Error generating presigned URL for file '{}' in bucket '{}': {}", fileName, bucketName, e.getMessage());
            throw new RuntimeException("Error generating file link", e);
        }
    }
}
