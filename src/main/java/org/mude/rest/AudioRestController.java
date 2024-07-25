package org.mude.rest;


import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/api/minio/audio")
public class AudioRestController {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.audio.bucket.name}")
    private  String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMusic(@RequestParam("file") MultipartFile file) {
        log.info("Uploading music file: {}", file.getOriginalFilename());
        return uploadAudio(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadMusic(@PathVariable String filename) {
        log.info("Downloading music file: {}", filename);
        return downloadAudio(filename);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteMusic(@PathVariable String filename) {
        log.info("Deleting music file: {}", filename);
        return deleteFile(filename);
    }

    private ResponseEntity<String> uploadAudio(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .build());
            log.info("Audio uploaded successfully: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.CREATED).body("Audio uploaded successfully: " + file.getOriginalFilename());
        }
        catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in uploadMusic music: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading Audio: " + e.getMessage());
        }
    }

    private ResponseEntity<byte[]> downloadAudio(String filename) {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
            byte[] fileBytes = inputStream.readAllBytes();
            log.info("Audio downloaded successfully: {}", filename);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .body(fileBytes);
        }
        catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in downloadFile : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private ResponseEntity<String> deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(filename).build());
            log.info("Audio deleted successfully: {}", filename);
            return ResponseEntity.ok("Audio deleted successfully: " + filename);
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in deleteFile: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting audio: " + e.getMessage());
        }
    }
}