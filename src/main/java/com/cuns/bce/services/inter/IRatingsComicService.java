package com.cuns.bce.services.inter;

import com.cuns.bce.dto.response.api.RARatingsComicDto;
import com.cuns.bce.entities.RatingsComic;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IRatingsComicService {
    // add rating to comic
    void addRating(Principal principal, Long comicId, String content, Integer rate);
    // get rating of comic
    List<RatingsComic> getRatings(Long comicId);
    // check if user is rating this comic
    boolean isRating(Principal principal, Long comicId);
    // get count rating of comic
    int getCountRating(Long comicId);
    // get all user rating to comic
    List<RARatingsComicDto> getAllRatingsComicByComicId(Long comicId);
}
