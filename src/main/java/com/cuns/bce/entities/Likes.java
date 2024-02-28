package com.cuns.bce.entities;

import com.cuns.bce.entities.classid.LikesId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(LikesId.class)
@Table(name = "likes")
public class Likes {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likers_id", nullable = false)
    private User liker;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liking_id", nullable = false)
    private User liking;

    @Column(name = "like_date")
    private OffsetDateTime likeDate;

    public Likes(User liker, User liking) {
        this.liker = liker;
        this.liking = liking;
        this.likeDate = OffsetDateTime.now();
    }

    public Likes() {

    }

}