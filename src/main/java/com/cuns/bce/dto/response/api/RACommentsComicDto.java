package com.cuns.bce.dto.response.api;

import com.cuns.bce.dto.response.comics.CommentsComicDto;
import com.cuns.bce.func.Funcs;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.cuns.bce.entities.CommentsComic}
 */
@Data
@RequiredArgsConstructor
public class RACommentsComicDto implements Serializable {
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
    Boolean isActive;
    Boolean isParent;
    OffsetDateTime createdAt;
    List<CommentsComicDto> parentCmtsComicReplies;

    public String getTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
}