package org.mude.model.generics;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;


import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@MappedSuperclass
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class GenericItem {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "link", nullable = false)
    private String link;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "description")
    private String description;
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    @ElementCollection
    @Column(name = "tags",nullable = false)
    private Set<String> tags;
    @Column(name = "owner")
    private String owner;
}

