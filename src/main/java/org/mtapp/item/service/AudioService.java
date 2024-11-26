package org.mtapp.item.service;

import org.mtapp.item.model.AudioItem;

import java.util.List;
import java.util.UUID;

public interface AudioService {
    AudioItem createAudioItem(AudioItem musicItem);
    List<AudioItem> getAudioItemsByArtist(String artist);
    List<AudioItem> getAudioItemsByTitle(String title);
    List<AudioItem> getAudioItemsByGenre(String genre);
    List<AudioItem> getAllAudioItems();
    AudioItem getAudioItemById(UUID id);
}
