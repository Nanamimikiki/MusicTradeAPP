package org.mtapp.item.model.base;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class BaseItem {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "description")
    private String description;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @ElementCollection
    @CollectionTable(name = "item_tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "tag", nullable = false)
    private Set<String> tags;

    @Column(name = "owner")
    private String owner;

    @Column(name = "artist")
    private String artist;

    @Column(name = "genre")
    private String genre;

    @Column(name = "thumbnail_link")
    private String thumbnailLink;

    @Column(name = "release_date")
    private Instant releaseDate;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
