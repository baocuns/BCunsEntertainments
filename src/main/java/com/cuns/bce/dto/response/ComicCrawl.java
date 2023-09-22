package com.cuns.bce.dto.response;

import lombok.Data;

@Data
public class ComicCrawl {
    private String crawlId;
    private String title;
    private String thumbnails;
    private String description;
    private String chapterId;
}
