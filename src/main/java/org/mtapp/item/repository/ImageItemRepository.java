package org.mtapp.item.repository;


import org.mtapp.item.model.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ImageItemRepository extends JpaRepository<ImageItem, UUID> {
    Optional<ImageItem> findImageItemByTitle(String title);
}
