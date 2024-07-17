package org.mude.rest;

import org.mude.service.MinioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
public class MinioRestController {

    @Autowired
    private MinioServiceImpl minioService;

    @PostMapping("/upload")
    public String uploadFileToMinIO(@RequestParam("file") MultipartFile file) {
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            String fileName = file.getOriginalFilename();
            minioService.uploadFile(fileName, in);
            return "File uploaded.";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Something wrong.";
    }

    @GetMapping("/download")
    public String downloadFile() throws Exception {
        return minioService.downloadFile("file.txt");
    }
}

