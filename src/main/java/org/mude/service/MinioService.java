package org.mude.service;

import java.io.InputStream;

public interface MinioService {
    void uploadFile(String objectName, InputStream inputStream);
    String  downloadFile(String objectName);
}
