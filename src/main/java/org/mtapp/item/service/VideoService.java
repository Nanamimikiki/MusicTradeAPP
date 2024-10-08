package org.mtapp.item.service;

import org.mtapp.item.model.VideoItem;

import java.util.List;
import java.util.UUID;

public interface VideoService {
    VideoItem createVideoItem(VideoItem videoItem);

    List<VideoItem> getVideoItemsByArtist(String artist);

    List<VideoItem> getVideoItemsByTitle(String title);

    List<VideoItem> getVideoItemsByGenre(String genre);

    List<VideoItem> getAllVideoItems();

    VideoItem getVideoItemById(UUID id);
}
