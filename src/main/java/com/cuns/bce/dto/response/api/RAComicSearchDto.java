package com.cuns.bce.dto.response.api;

import com.cuns.bce.func.Funcs;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.cuns.bce.entities.Comic}
 */
@Data
@RequiredArgsConstructor
public class RAComicSearchDto implements Serializable {
    Long id;
    String title;
    String description;
    String thumbnails;
    Long view;
    OffsetDateTime updatedAt;
    Long countChapter;
    String slug;

    public String getTimeAgo() {
        return Funcs.getTimeAgo(updatedAt);
    }
    public String getTitleSlug() {
        return slug + "-" + id;
    }
}