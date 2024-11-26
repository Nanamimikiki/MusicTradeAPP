package org.mtapp.item.service.impl;

import org.mtapp.item.model.ImageItem;
import org.mtapp.item.repository.ImageItemRepository;
import org.mtapp.minio.service.MinioService;
import org.mtapp.user.model.User;
import org.mtapp.user.repository.UserRepository;
import org.mtapp.item.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageItemRepository imageItemRepository;
    private final MinioService minioService;
    private final UserRepository userRepository;
    private final String bucketName;

    public ImageServiceImpl(ImageItemRepository imageItemRepository,
                            MinioService minioService,
                            UserRepository userRepository,
                            @Value("${minio.image.bucket-name}") String bucketName) {
        this.imageItemRepository = imageItemRepository;
        this.minioService = minioService;
        this.userRepository = userRepository;
        this.bucketName = bucketName;
    }

    @Override
    public ImageItem uploadImageItem(MultipartFile file, String fileName, UUID ownerId, Set<String> tags) throws Exception {
        if (minioService.fileExists(bucketName, fileName)) {
            throw new IllegalArgumentException("File already exists: " + fileName);
        }
        minioService.uploadFile(bucketName, fileName, file);

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + ownerId));

        ImageItem imageItem = new ImageItem();
        imageItem.setTitle(fileName);
        imageItem.setLink(minioService.getFileLink(bucketName, fileName));
        imageItem.setCreatedAt(Date.from(Instant.now()));
        imageItem.setOwner(owner.getUsername());
        imageItem.setTags(tags);

        return imageItemRepository.save(imageItem);
    }

    @Override
    public Optional<ImageItem> downloadImageItem(String fileName) {
        return imageItemRepository.findImageItemByTitle(fileName);
    }

    @Override
    public boolean imageExists(String fileName) throws Exception {
        return minioService.fileExists(bucketName, fileName);
    }
}
