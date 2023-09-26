package com.cuns.bce.dto.response.comics;

import com.cuns.bce.entities.Chapter;
import com.cuns.bce.entities.Rating;
import com.cuns.bce.func.Funcs;
import com.github.slugify.Slugify;
import lombok.*;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DTO for {@link com.cuns.bce.entities.Comic}
 */
@RequiredArgsConstructor
@Data
public class ComicDto implements Serializable {
    Long id;
    String title;
    String description;
    String thumbnails;
    Map<String, Object> like;
    Long view;
    String author;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    Long countChapter;
    String slug;
    Set<Chapter> chapters;

    public String getUpdateTimeAgo() {
        return Funcs.getTimeAgo(updatedAt);
    }
    public String getCreateTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
    public String getTileSlug() {
        return slug + "-" + id;
    }
    public String getSlugChapterNext(Chapter chapter) {
        // get chapter prev by index of chapters
        int index = Funcs.getChapterIndex(chapters, chapter);
        if (index == 0) {
            return null;
        }
        Chapter prevChapter = (Chapter) chapters.toArray()[index - 1];
        return "/comics/" +  slug + "-" + id + "/chapter/" + prevChapter.getId();
    }
    public String getSlugChapterPrev(Chapter chapter) {
        // get chapter next by index of chapters
        int index = Funcs.getChapterIndex(chapters, chapter);
        if (index == chapters.size() - 1) {
            return null;
        }
        Chapter nextChapter = (Chapter) chapters.toArray()[index + 1];
        return "/comics/" + slug + "-" + id + "/chapter/" + nextChapter.getId();
    }
    public List<List<Chapter>> getChunks() {
        return Funcs.chunkChapters(chapters, 50);
    }
}