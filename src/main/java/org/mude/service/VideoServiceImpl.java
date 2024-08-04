package org.mude.service;

import lombok.extern.slf4j.Slf4j;
import org.mude.model.VideoItem;
import org.mude.repos.VideoItemRepository;
import org.mude.service.i.VideoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    private VideoItemRepository videoItemRepository;

    @Override
    public VideoItem createVideoItem(VideoItem videoItem) {
        log.info("Creating video item: {}", videoItem);
        return videoItemRepository.save(videoItem);
    }

    @Override
    public List<VideoItem> getVideoItemsByArtist(String artist) {
        List<VideoItem> videos = new ArrayList<>(videoItemRepository.findAll().stream()
                .filter(videoItem -> videoItem.getOwner().equals(artist)).toList());
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