package com.cuns.bce.dto.response.comics;

import com.cuns.bce.func.Funcs;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * DTO for {@link com.cuns.bce.entities.Comic}
 */
@Getter
@Setter
@RequiredArgsConstructor
@Data
public class ComicsDto implements Serializable {
    Long id;
    String title;
    String thumbnails;
    Map<String, Object> like;
    Long view;
    Long countChapter;
    String slug;
    OffsetDateTime updatedAt;

    public String getTimeAgo() {
        return Funcs.getTimeAgo(updatedAt);
    }
    public String getTitleSlug() {
        return slug + "-" + id;
    }
}