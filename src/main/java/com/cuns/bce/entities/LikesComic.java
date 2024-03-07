package com.cuns.bce.entities;

import com.cuns.bce.entities.classid.LikesComicId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(LikesComicId.class)
@Table(name = "likes_comics")
public class LikesComic {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    private Comic comic;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public LikesComic(User user, Comic comic) {
        this.user = user;
        this.comic = comic;
        this.createdAt = OffsetDateTime.now();
    }
    public LikesComic() {
    }

}