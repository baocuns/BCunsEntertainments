package com.cuns.bce.dto.response.comics;

import com.cuns.bce.func.Funcs;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.cuns.bce.entities.CommentsComic}
 */
@Data
@RequiredArgsConstructor
public class CommentsComicDto implements Serializable {
    Long id;
    Long userProfileId;
    String userProfileBcId;
    String userProfileFullname;
    String userProfileAvatarUrl;
    Long chapterId;
    String chapterTitle;
    String content;
    Long likes;
    Long dislikes;
    Long reports;
    Boolean isActive;
    Boolean isParent;
    OffsetDateTime createdAt;
    Integer totalReplies;

    public String getTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
}