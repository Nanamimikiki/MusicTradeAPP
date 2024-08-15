package org.mtapp.item.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mtapp.item.model.VideoItem;
import org.mtapp.item.repository.VideoItemRepository;
import org.mtapp.item.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoItemRepository videoItemRepository;

    @Override
    public VideoItem createVideoItem(VideoItem videoItem) {
        log.info("Creating video item: {}", videoItem);
        return videoItemRepository.save(videoItem);
    }

    @Override
    public List<VideoItem> getVideoItemsByArtist(String artist) {
        List<VideoItem> videos = new ArrayList<>(videoItemRepository.findAll().stream()
                .filter(videoItem -> videoItem.getArtist().equals(artist)).toList());
        log.info("Found {} videos by artist {}", videos.size(), artist);
        return videos;
    }

    @Override
    public List<VideoItem> getVideoItemsByTitle(String title) {
        List<VideoItem> videos = new ArrayList<>(videoItemRepository.findAll().stream()
                .filter(videoItem -> videoItem.getTitle().equals(title)).toList());
        log.info("Found {} videos with title {}", videos.size(), title);
        return videos;
    }

    @Override
    public List<VideoItem> getVideoItemsByGenre(String genre) {
        List<VideoItem> videos = new ArrayList<>(videoItemRepository.findAll().stream()
                .filter(videoItem -> videoItem.getGenre().equals(genre)).toList());
        log.info("Found {} videos with genre {}", videos.size(), genre);
        return videos;
    }

    @Override
    public List<VideoItem> getAllVideoItems() {
        List<VideoItem> videos = new ArrayList<>(videoItemRepository.findAll());
        log.info("Found {} videos ", videos.size());
        return videos;
    }

    @Override
    public VideoItem getVideoItemById(UUID id) {
        VideoItem videoItem = new VideoItem();
        videoItem = videoItemRepository.findById(id).orElse(null);
        if (videoItem != null) {
            log.info("Found video item with id {}", id);
            return videoItemRepository.getReferenceById(id);
        }
        log.info("Video item with ID {} not found", id);
        return null;
    }
}