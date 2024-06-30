package org.mude.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id ;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "created")
    private Date createdAt;
    @Column(name = "updated")
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_auido_items",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "audio_item_id", referencedColumnName = "id")})
    private Set<AudioItem> audioItems;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_video_items",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "video_item_id", referencedColumnName = "id")})
    private Set<VideoItem> videoItems;
}

