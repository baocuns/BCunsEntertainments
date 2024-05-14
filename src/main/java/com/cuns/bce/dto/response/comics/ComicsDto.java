package com.cuns.bce.dto.response.comics;

import com.cuns.bce.entities.CrawlsConfig;
import com.cuns.bce.entities.LikesComic;
import com.cuns.bce.entities.RatingsComic;
import com.cuns.bce.func.Funcs;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;

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
    Long view;
    Long countChapter;
    String slug;
    OffsetDateTime updatedAt;
    Set<RatingsComic> ratingsComics;
    Set<LikesComic> likesComics;

    public String getTimeAgo() {
        return Funcs.getTimeAgo(updatedAt);
    }
    public String getTitleSlug() {
        return slug + "-" + id;
    }
    // avg rating of comic
    public Double getAvgRating() {
        if (ratingsComics == null || ratingsComics.isEmpty()) {
            return 0.0;
        }
        return ratingsComics.stream().mapToDouble(RatingsComic::getRate).average().orElse(0.0);
    }
    public void setComicByConfig(CrawlsConfig crawlsConfig) {
        this.thumbnails = "https://" + crawlsConfig.getDomainServer() + this.thumbnails;
    }
}