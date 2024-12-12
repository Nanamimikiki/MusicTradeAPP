package org.mtapp.item.rest;

import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/api/minio/video")
public class VideoRestController {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.video.bucket-name}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        log.info("Uploading video file: {}", file.getOriginalFilename());
        return uploadFile(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadVideo(@PathVariable String filename) {
        log.info("Downloading video file: {}", filename);
        return downloadFile(filename);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteVideo(@PathVariable String filename) {
        log.info("Deleting video file: {}", filename);
        return deleteFile(filename);
    }

    private ResponseEntity<String> uploadFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .build());
            log.info("Video uploaded successfully: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.CREATED).body("Video uploaded successfully: " + file.getOriginalFilename());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in uploadVideo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading Video: " + e.getMessage());
        }
    }

    private ResponseEntity<byte[]> downloadFile(String filename) {
        try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build())) {
            byte[] fileBytes = inputStream.readAllBytes();
            log.info("Video downloaded successfully: {}", filename);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .body(fileBytes);
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in downloadFile: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<String> deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(filename).build());
            log.info("Video deleted successfully: {}", filename);
            return ResponseEntity.ok("Video deleted successfully: " + filename);
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error in deleteFile: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting video: " + e.getMessage());
        }
    }
}
