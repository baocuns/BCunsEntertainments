package com.cuns.bce.repositories;

import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.RatingsComic;
import com.cuns.bce.entities.User;
import com.cuns.bce.entities.classid.RatingsComicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RatingsComicRepository extends JpaRepository<RatingsComic, RatingsComicId>, JpaSpecificationExecutor<RatingsComic> {
    // get rating of comic
    List<RatingsComic> findByComicId(Long comicId);
    // check if user is rating this comic
    RatingsComic findByUserAndComic(User user, Comic comic);
    // get all user rating to comic
//    List<RatingsComic> findByComicId(Long comicId);
}