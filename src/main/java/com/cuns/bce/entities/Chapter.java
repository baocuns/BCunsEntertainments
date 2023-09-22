package com.cuns.bce.entities;

import com.cuns.bce.func.Funcs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    private Comic comic;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "view")
    private Long view;

    @Column(name = "crawl_id", length = Integer.MAX_VALUE)
    private String crawlId;

    @Column(name = "is_updated")
    private Boolean isUpdated;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "chapter")
    private Set<Photo> photos = new LinkedHashSet<>();

    public String getTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
}