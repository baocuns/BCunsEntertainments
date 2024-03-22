package com.cuns.bce.dto.response.comics;

import com.cuns.bce.func.Funcs;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DTO for {@link com.cuns.bce.entities.Chapter}
 */
@RequiredArgsConstructor
@Data
public class ChapterDto implements Serializable {
    Long id;
    String title;
    String description;
    Long view;
    OffsetDateTime createdAt;
    public String getTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
    public Integer getChapter() {
        return Funcs.extractNumberInt(title);
    }
}