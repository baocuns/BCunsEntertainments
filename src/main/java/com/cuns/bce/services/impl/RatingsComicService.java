package com.cuns.bce.services.impl;

import com.cuns.bce.dto.response.api.RARatingsComicDto;
import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.RatingsComic;
import com.cuns.bce.entities.User;
import com.cuns.bce.repositories.RatingsComicRepository;
import com.cuns.bce.services.inter.IRatingsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingsComicService implements IRatingsComicService {
    private final ModelMapper modelMapper = new ModelMapper();
    final private RatingsComicRepository ratingsComicRepository;
    final private UserService userService;
    final private ComicService comicService;

    @Override
    public void addRating(Principal principal, Long comicId, String content, Integer rate) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic by comicId
        Comic comic = comicService.findById(comicId, true);
        // check if user is rating this comic
        if (ratingsComicRepository.findByUserAndComic(user, comic) != null) {
            log.info("User is rating this comic");
            return;
        }

        // add rating to comic
        ratingsComicRepository.save(new RatingsComic(user, comic, content, rate));
    }

    @Override
    public List<RatingsComic> getRatings(Long comicId) {
        return ratingsComicRepository.findByComicId(comicId);
    }

    @Override
    public boolean isRating(Principal principal, Long comicId) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic by comicId
        Comic comic = comicService.findById(comicId, true);
        // check if user is rating this comic
        return ratingsComicRepository.findByUserAndComic(user, comic) != null;
    }

    @Override
    public int getCountRating(Long comicId) {
        return ratingsComicRepository.findByComicId(comicId).size();
    }

    @Override
    public List<RARatingsComicDto> getAllRatingsComicByComicId(Long comicId) {
        List<RatingsComic> ratings = ratingsComicRepository.findByComicId(comicId);
        List<RARatingsComicDto> apiRatingsDto = new ArrayList<>();
        for (RatingsComic rating : ratings) {
            apiRatingsDto.add(modelMapper.map(rating, RARatingsComicDto.class));
        }
        return apiRatingsDto;
    }
}
