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


@RestController
@RequestMapping("api/images")
@Slf4j
public class ImageRestController {
    @Value("${minio.image.bucket.name}")
    private String bucketName;
    @Autowired
    private MinioServiceImpl minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("fileName") String fileName) {
        try {
            if (!minioService.fileExists(bucketName, fileName)) {
                minioService.uploadFile(bucketName, fileName, file);
                log.info("In uploadImage - {} uploaded successfully", fileName);
                return ResponseEntity.ok("Image uploaded successfully.");
            } else {
                log.info("Image with name - {} already exists!", fileName);
                return ResponseEntity.badRequest().body("File already exists.");
            }
        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName, String bucketName) {
        try {
            byte[] fileBytes = minioService.downloadFile(bucketName, fileName);
            if (fileBytes != null) {
                log.info("Image {} downloaded successfully", fileName);
                return ResponseEntity.ok().body(fileBytes);
            } else {
                log.info("Image {} not found!", fileName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error downloading file: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}