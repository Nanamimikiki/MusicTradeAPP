package org.mtapp.item.service;

import org.mtapp.item.model.ImageItem;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ImageService {
    ImageItem uploadImageItem(MultipartFile file, String fileName, UUID ownerId, Set<String> tags) throws Exception;

    Optional<ImageItem> downloadImageItem(String fileName);

    boolean imageExists(String fileName) throws Exception;
}
