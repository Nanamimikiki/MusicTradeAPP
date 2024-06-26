package org.mude.service;

import lombok.extern.slf4j.Slf4j;
import org.mude.model.MusicItem;
import org.mude.repos.MusicItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Slf4j

public class MusicServiceImpl implements MusicService {

    @Autowired
    MusicItemRepository musicItemRepository;

    @Override
    public MusicItem createMusicItem(MusicItem musicItem) {
        return musicItemRepository.save(musicItem);
    }

    @Override
    public List<MusicItem> getMusicItemsByArtist(String artist) {
        List<MusicItem> allMusicByArtist = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getArtist().equals(artist)).toList();
        log.info("In getMusicItemsByArtist - {} music items found", allMusicByArtist.size());
        return allMusicByArtist;
    }

    @Override
    public List<MusicItem> getMusicItemsByTitle(String title) {
        List<MusicItem> allMusicWithProvidedTitle = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getTitle().equals(title)).toList();
        log.info("In getMusicItemsByTitle - {} music items found", allMusicWithProvidedTitle.size());
        return allMusicWithProvidedTitle;
    }

    @Override
    public List<MusicItem> getMusicItemsByGenre(String genre) {
        List<MusicItem> allMusicWithProvidedGenre = musicItemRepository.findAll().stream()
                .filter(MusicItem -> MusicItem.getGenre().equals(genre)).toList();
        log.info("In getMusicItemsByGenre - {} music items found", allMusicWithProvidedGenre.size());
        return allMusicWithProvidedGenre;
    }

    @Override
    public List<MusicItem> getAllMusicItems() {
        List<MusicItem> allMusicItems = musicItemRepository.findAll();
        log.info("In getAllMusicItems - {} music items found", allMusicItems.size());
        return allMusicItems;
    }
    @Override
    public MusicItem getMusicItemById(UUID id) {
        MusicItem musicItem = musicItemRepository.findById(id).orElse(null);
        if (musicItem == null) {
            log.info("In getMusicItemById - music item with ID {} was not found", id);
            return null;
        }
        return musicItem;
    }
}
