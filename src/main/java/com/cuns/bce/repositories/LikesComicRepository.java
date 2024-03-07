package com.cuns.bce.repositories;

import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.LikesComic;
import com.cuns.bce.entities.User;
import com.cuns.bce.entities.classid.LikesComicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LikesComicRepository extends JpaRepository<LikesComic, LikesComicId>, JpaSpecificationExecutor<LikesComic> {
    LikesComic findByUserAndComic(User user, Comic comic);
    // get count likes comic
    int countByComicId(Long comicId);
}