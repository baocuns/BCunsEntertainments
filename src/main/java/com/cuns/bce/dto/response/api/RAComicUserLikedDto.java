package com.cuns.bce.dto.response.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * DTO for {@link com.cuns.bce.entities.User}
 */
@Data
@RequiredArgsConstructor
public class RAComicUserLikedDto implements Serializable {
    Set<LikesComicDto> likesComics;

    /**
     * DTO for {@link com.cuns.bce.entities.LikesComic}
     */
    @Data
    @RequiredArgsConstructor
    public static class LikesComicDto implements Serializable {
        RAComicSearchDto comic;
        OffsetDateTime createdAt;
    }
}