package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.base.BaseItem;

import java.util.Date;

@Data
@Entity
@Table(name = "audio")
public class AudioItem extends BaseItem {
    @Column(name = "artist")
    private String artist;
    @Column(name = "album")
    private String album;
    @Column(name = "genre")
    private String genre;
    @Column(name = "thumbnail_link")
    private String thumbnailLink;
    @Column(name = "release_date")
    private Date releaseDate;
}
