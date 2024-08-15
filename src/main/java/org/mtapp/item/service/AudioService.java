package org.mtapp.item.service;

import org.mtapp.item.model.AudioItem;

import java.util.List;
import java.util.UUID;

public interface AudioService {
    AudioItem createMusicItem(AudioItem musicItem);
    List<AudioItem> getMusicItemsByArtist(String artist);
    List<AudioItem> getMusicItemsByTitle(String title);
    List<AudioItem> getMusicItemsByGenre(String genre);
    List<AudioItem> getAllMusicItems();
    AudioItem getMusicItemById(UUID id);
}
