package org.mude.repos;

import org.mude.model.MusicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MusicItemRepository extends JpaRepository<MusicItem, UUID> {
    Optional <MusicItem> findMusicItemById(UUID id);
}
