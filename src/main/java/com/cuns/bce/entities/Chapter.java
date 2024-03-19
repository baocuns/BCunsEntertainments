package com.cuns.bce.entities;

import com.cuns.bce.func.Funcs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @OneToMany(mappedBy = "chapter")
    private Set<CommentsComic> commentsComics = new LinkedHashSet<>();

    public String getTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
    public Integer extractNumber() {
        Pattern pattern = Pattern.compile("\\d+"); // Tìm các chữ số trong chuỗi
        Matcher matcher = pattern.matcher(title);

        if (matcher.find()) {
            String numberStr = matcher.group();
            return Integer.parseInt(numberStr);
        } else {
            return -1;
        }
    }
}