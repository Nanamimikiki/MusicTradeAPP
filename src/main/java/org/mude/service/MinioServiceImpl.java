package org.mude.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    @Autowired
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public void uploadFile(String objectName, InputStream inputStream) {
        try {
            minioClient.putObject(PutObjectArgs.builder().object(objectName)
                    .stream(inputStream, -1, 10485760).build());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    @Override
    public String downloadFile(String objectName) {
        try (InputStream stream = minioClient
                .getObject(GetObjectArgs.builder()
                        .object(objectName)
                        .build());) {
            return new String(stream.readAllBytes());
        } catch (IOException | InsufficientDataException | InvalidResponseException | ServerException |
                 XmlParserException | NoSuchAlgorithmException | InvalidKeyException | ErrorResponseException |
                 InternalException e) {
            e.printStackTrace();
        }
        return "You haven't uploaded anything yet.";
    }
}