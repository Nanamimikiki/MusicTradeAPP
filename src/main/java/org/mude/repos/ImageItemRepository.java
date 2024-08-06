package org.mude.repos;


import org.mude.model.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageItemRepository extends JpaRepository<ImageItem, UUID> {
    Optional<ImageItem> findImageItemByTitle(String title);
}
