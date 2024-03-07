package com.cuns.bce.services.inter;

import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface ILikesComicService {
    // add like and unlike to comic
    void addLikeAndUnlike(Principal principal, Long comicId);
    // check if user is liking this comic
    boolean isLiking(Principal principal, Long comicId);
    // get count like of comic
    int getCountLike(Long comicId);
    // get all user like to comic
    //List<RALikesComicDto> getAllLikesComicByComicId(Long comicId);
}
