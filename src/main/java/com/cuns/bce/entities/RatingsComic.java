package com.cuns.bce.entities;

import com.cuns.bce.entities.classid.RatingsComicId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@IdClass(RatingsComicId.class)
@Table(name = "ratings_comics")
public class RatingsComic {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    private Comic comic;

    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // constructor
    public RatingsComic() {
    }
    public RatingsComic(User user, Comic comic, String content, Integer rate) {
        this.user = user;
        this.comic = comic;
        this.content = content;
        this.rate = rate;
        this.isActive = true;
        this.createdAt = OffsetDateTime.now();
    }
}