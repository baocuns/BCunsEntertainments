package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // constructor
    public User() {
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @OneToMany(mappedBy = "user")
    private Set<Authority> authorities = new LinkedHashSet<>();

    @OneToOne(mappedBy = "uid")
    private Profile profile;

    @OneToMany(mappedBy = "uid")
    private Set<Comic> comics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<RatingsComic> ratingsComics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<LikesComic> likesComics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<Follows> followers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "following")
    private Set<Follows> followings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<CommentsComic> commentsComics = new LinkedHashSet<>();

}