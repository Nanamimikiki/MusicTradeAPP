package org.mude.service.i;

import org.mude.model.VideoItem;

import java.util.List;
import java.util.UUID;

public interface VideoService {
    VideoItem createMusicItem(VideoItem videoItemm);
    List<VideoItem> getMusicItemsByArtist(String artist);
    List<VideoItem> getMusicItemsByTitle(String title);
    List<VideoItem> getMusicItemsByGenre(String genre);
    List<VideoItem> getAllMusicItems();
    VideoItem getMusicItemById(UUID id);
}
