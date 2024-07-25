package org.mude.service.i;

import org.hibernate.cache.spi.support.StorageAccess;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    void uploadFile(String objectName, String bucketName, MultipartFile file);

    byte[]  downloadFile(String objectName, String bucketName);

    boolean fileExists(String bucketName, String filename);
}
