package org.mude.service;

import org.mude.model.MusicItem;

import java.util.List;
import java.util.UUID;

public interface MusicService {
    MusicItem createMusicItem(MusicItem musicItem);
    List<MusicItem> getMusicItemsByArtist(String artist);
    List<MusicItem> getMusicItemsByTitle(String title);
    List<MusicItem> getMusicItemsByGenre(String genre);
    List<MusicItem> getAllMusicItems();
    MusicItem getMusicItemById(UUID id);
}
