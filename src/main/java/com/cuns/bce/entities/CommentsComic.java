package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "comments_comics")
public class CommentsComic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    private Comic comic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "likes")
    private Long likes = 0L;

    @Column(name = "dislikes")
    private Long dislikes = 0L;

    @Column(name = "reports")
    private Long reports = 0L;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_parent")
    private Boolean isParent;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<ReplyCmtsComic> parentCmtsComics = new LinkedHashSet<>();

    @OneToOne(mappedBy = "reply")
    private ReplyCmtsComic replyCmtsComic;

    public CommentsComic() {
    }
    public CommentsComic(User user, Comic comic, Chapter chapter, String content, Boolean isParent) {
        this.user = user;
        this.comic = comic;
        this.chapter = chapter;
        this.content = content;
        this.isActive = true;
        this.isParent = isParent;
        this.createdAt = OffsetDateTime.now();
    }
}