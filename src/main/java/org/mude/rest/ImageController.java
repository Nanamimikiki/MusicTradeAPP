package org.mude.rest;

import lombok.extern.slf4j.Slf4j;
import org.mude.service.MinioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {
    @Value("${minio.image.bucket.name}")
    private String bucketName;
    @Autowired
    private MinioServiceImpl minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file,
                                              @RequestParam("filename") String filename) {
        try {
            if (!minioService.fileExists(bucketName, filename)) {
                minioService.uploadFile(bucketName, filename, file);
                log.info("Image uploaded successfully");
                return ResponseEntity.ok("Image uploaded successfully.");
            } else {
                return ResponseEntity.badRequest().body("File already exists.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }
    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("filename") String filename, String bucketName) {
        try {
            byte[] fileBytes = minioService.downloadFile(bucketName, filename);
            if (fileBytes!= null) {
                return ResponseEntity.ok().body(fileBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}