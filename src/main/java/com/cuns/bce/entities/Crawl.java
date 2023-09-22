package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "crawls")
public class Crawl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "crawl_id", nullable = false, length = Integer.MAX_VALUE)
    private String crawlId;

    @Column(name = "chapter_id", nullable = false, length = Integer.MAX_VALUE)
    private String chapterId;

    @Column(name = "count_chapter", length = Integer.MAX_VALUE)
    private String countChapter;

    @Column(name = "is_updated")
    private Boolean isUpdated;

    @OneToMany(mappedBy = "crawl")
    private Set<Comic> comics = new LinkedHashSet<>();

}