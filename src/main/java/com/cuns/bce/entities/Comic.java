package com.cuns.bce.entities;

import com.cuns.bce.func.Funcs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User uid;

    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "thumbnails", length = Integer.MAX_VALUE)
    private String thumbnails;

    @Column(name = "view")
    private Long view;

    @Column(name = "author", length = Integer.MAX_VALUE)
    private String author;

    @Column(name = "is_publish")
    private Boolean isPublish;

    @Column(name = "is_nominate")
    private Boolean isNominate;

    @Column(name = "is_updated")
    private Boolean isUpdated;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "count_chapter")
    private Long countChapter;

    @Column(name = "slug", length = Integer.MAX_VALUE)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crawl_id")
    private Crawl crawl;

    @OneToMany(mappedBy = "comic")
    private Set<CategoriesComic> categoriesComics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "comic")
    private Set<Chapter> chapters = new LinkedHashSet<>();

    @OneToMany(mappedBy = "comic")
    private Set<RatingsComic> ratingsComics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "comic")
    private Set<LikesComic> likesComics = new LinkedHashSet<>();
}