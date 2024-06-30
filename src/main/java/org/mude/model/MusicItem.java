package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.generics.GenericItem;

import java.util.Date;

@Data
@Entity
@Table(name = "music")
public class MusicItem extends GenericItem {
    @Column(name = "artist")
    private String artist;
    @Column(name = "album")
    private String album;
    @Column(name = "genre")
    private String genre;
    @Column(name = "release_date")
    private Date releaseDate;
}
