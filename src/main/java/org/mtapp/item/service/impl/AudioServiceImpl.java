package org.mtapp.item.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.mtapp.item.model.AudioItem;
import org.mtapp.item.repository.AudioItemRepository;
import org.mtapp.item.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Slf4j
public class AudioServiceImpl implements AudioService {
    @Autowired
    AudioItemRepository musicItemRepository;

    @Override
    public AudioItem createMusicItem(AudioItem musicItem) {
        return musicItemRepository.save(musicItem);
    }

    @Override
    public List<AudioItem> getMusicItemsByArtist(String artist) {
        List<AudioItem> allMusicByArtist = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getArtist().equals(artist)).toList();
        log.info("In getMusicItemsByArtist - {} music items found", allMusicByArtist.size());
        return allMusicByArtist;
    }

    @Override
    public List<AudioItem> getMusicItemsByTitle(String title) {
        List<AudioItem> allMusicWithProvidedTitle = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getTitle().equals(title)).toList();
        log.info("In getMusicItemsByTitle - {} music items found", allMusicWithProvidedTitle.size());
        return allMusicWithProvidedTitle;
    }

    @Override
    public List<AudioItem> getMusicItemsByGenre(String genre) {
        List<AudioItem> allMusicWithProvidedGenre = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getGenre().equals(genre)).toList();
        log.info("In getMusicItemsByGenre - {} music items found", allMusicWithProvidedGenre.size());
        return allMusicWithProvidedGenre;
    }

    @Override
    public List<AudioItem> getAllMusicItems() {
        List<AudioItem> allMusicItems = musicItemRepository.findAll();
        log.info("In getAllMusicItems - {} music items found", allMusicItems.size());
        return allMusicItems;
    }
    @Override
    public AudioItem getMusicItemById(UUID id) {
        AudioItem musicItem = musicItemRepository.findById(id).orElse(null);
        if (musicItem == null) {
            log.info("In getMusicItemById - music item with ID {} was not found", id);
            return null;
        }
        return musicItem;
    }
}
