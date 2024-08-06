package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.generics.GenericItem;

import java.util.Date;

@Data
@Table(name = "video")
@Entity
public class VideoItem extends GenericItem {
    @Column(name = "genre")
    public String genre;
    @Column(name = "artist")
    public String artist;
    @Column(name = "thumbnail_link")
    private String thumbnailLink;
    @Column(name = "release_date")
    private Date releaseDate;
}