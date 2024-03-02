package com.cuns.bce.dto.response.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

/**
 * DTO for {@link com.cuns.bce.entities.RatingsComic}
 */

@Data
@RequiredArgsConstructor
public class RARatingsComicDto {
    String userProfileFullname;
    String userProfileAvatarUrl;
    String content;
    Integer rate;
    Boolean isActive;
    OffsetDateTime createdAt;
}
