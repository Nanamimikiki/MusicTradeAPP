package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.base.BaseItem;

import java.util.Date;
@Data
@Entity
@Table(name = "images")
public class ImageItem extends BaseItem {
    @Column(name = "genre")
    public String genre;
    @Column(name = "artist")
    public String artist;
    @Column(name = "thumbnail_link")
    private String thumbnailLink;
    @Column(name = "release_date")
    private Date releaseDate;
}
