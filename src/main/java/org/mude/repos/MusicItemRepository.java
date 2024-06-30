package org.mude.repos;

import org.mude.model.AudioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicItemRepository extends JpaRepository<AudioItem, UUID> {
}
