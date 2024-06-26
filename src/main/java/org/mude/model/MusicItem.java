package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mude.model.generics.GenericItem;

import java.util.Set;

@Data
@Entity
@Table(name = "music")
@DiscriminatorColumn(name = "MUSIC", discriminatorType = DiscriminatorType.STRING)
public class MusicItem extends GenericItem {
    @Column(name = "artist")
    private String artist;
    @Column(name = "album")
    private String album;
    @Column(name = "genre")
    private String genre;
    @Column(name = "release_date")
    private int releaseDate;
}
