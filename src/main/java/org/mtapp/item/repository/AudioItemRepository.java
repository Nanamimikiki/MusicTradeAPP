package org.mtapp.item.repository;

import org.mtapp.item.model.AudioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AudioItemRepository extends JpaRepository<AudioItem, UUID> {
    Optional<AudioItem> findByTitle(String title);
    List<AudioItem> findByArtist(String artist);
    List<AudioItem> findByGenre(String genre);
}
