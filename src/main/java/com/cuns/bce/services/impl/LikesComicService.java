package com.cuns.bce.services.impl;

import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.LikesComic;
import com.cuns.bce.entities.User;
import com.cuns.bce.repositories.LikesComicRepository;
import com.cuns.bce.services.inter.ILikesComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesComicService implements ILikesComicService {
    private final ModelMapper modelMapper = new ModelMapper();
    final private UserService userService;
    final private ComicService comicService;
    final private LikesComicRepository likesComicRepository;

    @Override
    public void addLikeAndUnlike(Principal principal, Long comicId) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic by comicId
        Comic comic = comicService.findById(comicId, true);
        // check if user is liking this comic
        LikesComic likesComic = likesComicRepository.findByUserAndComic(user, comic);
        if (likesComic != null) {
            // unlike to comic
            likesComicRepository.delete(likesComic);
        } else {
            // add like to comic
            likesComicRepository.save(new LikesComic(user, comic));
        }
    }

    @Override
    public boolean isLiking(Principal principal, Long comicId) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic by comicId
        Comic comic = comicService.findById(comicId, true);
        // check if user is liking this comic
        return likesComicRepository.findByUserAndComic(user, comic) != null;
    }

    @Override
    public int getCountLike(Long comicId) {
        // get count likes comic
        return likesComicRepository.countByComicId(comicId);
    }
}
