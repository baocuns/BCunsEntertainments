package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bc_id", nullable = false)
    private String bcId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User uid;

    @Column(name = "fullname", nullable = false, length = Integer.MAX_VALUE)
    private String fullname;

    @Column(name = "story", length = Integer.MAX_VALUE)
    private String story;

    @Column(name = "avatar_url", length = Integer.MAX_VALUE)
    private String avatarUrl;

    @Column(name = "is_block")
    private Boolean isBlock;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Profile() {
    }

    public Profile(String bcId, User uid, String fullname) {
        this.id = null;
        this.bcId = bcId + System.currentTimeMillis();
        this.uid = uid;
        this.fullname = fullname;
        this.story = "story...";
        this.avatarUrl = "https://static.vecteezy.com/system/resources/previews/031/606/479/non_2x/manager-avatar-ilustration-free-vector.jpg";
        this.isBlock = false;
        this.isPublic = true;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}