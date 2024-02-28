package com.cuns.bce.entities;

import com.cuns.bce.entities.classid.FollowsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "follows")
@IdClass(FollowsId.class)
public class Follows {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Column(name = "follow_date")
    private OffsetDateTime followDate;

    public Follows(User follower, User following) {
        this.follower = follower;
        this.following = following;
        this.followDate = OffsetDateTime.now();
    }

    public Follows() {

    }
}
