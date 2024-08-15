package org.mtapp.item.repository;

import org.mtapp.item.model.VideoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface VideoItemRepository extends JpaRepository<VideoItem, UUID> {
    Optional<VideoItem> findVideoItemByTitle(String title);
}
