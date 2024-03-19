package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reply_cmts_comics")
public class ReplyCmtsComic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private CommentsComic parent;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reply_id", nullable = false)
    private CommentsComic reply;

    public ReplyCmtsComic() {
    }
    public ReplyCmtsComic(CommentsComic parent, CommentsComic reply) {
        this.parent = parent;
        this.reply = reply;
    }

}