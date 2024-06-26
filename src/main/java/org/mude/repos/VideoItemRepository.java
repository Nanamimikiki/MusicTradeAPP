package org.mude.repos;

import org.mude.model.VideoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface VideoItemRepository extends JpaRepository<VideoItem, UUID> {
    Optional<VideoItem> findVideoItemByTitle(String title);
}
