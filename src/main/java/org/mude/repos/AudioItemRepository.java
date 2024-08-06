package org.mude.repos;

import org.mude.model.AudioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AudioItemRepository extends JpaRepository<AudioItem, UUID> {
    Optional<AudioItem> findAudioItemByTitle(String title);
}
