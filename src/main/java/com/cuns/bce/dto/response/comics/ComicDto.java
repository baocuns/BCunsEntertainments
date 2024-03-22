package com.cuns.bce.dto.response.comics;

import com.cuns.bce.entities.*;
import com.cuns.bce.func.Funcs;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
    Long view;
    String author;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    Long countChapter;
    String slug;
    Set<Chapter> chapters;
    Set<RatingsComic> ratingsComics;
    Set<CategoriesComic> categoriesComics;
    Set<LikesComic> likesComics;

    public String getUpdateTimeAgo() {
        return Funcs.getTimeAgo(updatedAt);
    }
    public String getCreateTimeAgo() {
        return Funcs.getTimeAgo(createdAt);
    }
    public String getTileSlug() {
        return slug + "-" + id;
    }
    // use for get next and prev chapter
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
    // avg rating of comic
    public Double getAvgRating() {
        if (ratingsComics == null || ratingsComics.isEmpty()) {
            return 0.0;
        }
        return ratingsComics.stream().mapToDouble(RatingsComic::getRate).average().orElse(0.0);
    }
}