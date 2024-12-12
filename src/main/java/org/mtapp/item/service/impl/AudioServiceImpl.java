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
    private AudioItemRepository audioItemRepository;

    @Override
    public AudioItem createAudioItem(AudioItem musicItem) {
        AudioItem savedItem = audioItemRepository.save(musicItem);
        log.info("Audio item '{}' created with ID {}", savedItem.getTitle(), savedItem.getId());
        return savedItem;
    }

    @Override
    public List<AudioItem> getAudioItemsByArtist(String artist) {
        List<AudioItem> items = audioItemRepository.findByArtist(artist);
        log.info("Found {} audio items by artist '{}'", items.size(), artist);
        return items;
    }

    @Override
    public List<AudioItem> getAudioItemsByTitle(String title) {
        List<AudioItem> items = audioItemRepository.findAll().stream()
                .filter(item -> item.getTitle().equals(title)).toList();
        log.info("Found {} audio items with title '{}'", items.size(), title);
        return items;
    }

    @Override
    public List<AudioItem> getAudioItemsByGenre(String genre) {
        List<AudioItem> items = audioItemRepository.findByGenre(genre);
        log.info("Found {} audio items with genre '{}'", items.size(), genre);
        return items;
    }

    @Override
    public List<AudioItem> getAllAudioItems() {
        List<AudioItem> allItems = audioItemRepository.findAll();
        log.info("Found {} audio items total", allItems.size());
        return allItems;
    }

    @Override
    public AudioItem getAudioItemById(UUID id) {
        return audioItemRepository.findById(id)
                .orElseGet(() -> {
                    log.info("No audio item found with ID {}", id);
                    return null;
                });
    }
}
