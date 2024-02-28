package com.cuns.bce.repositories;

import com.cuns.bce.entities.Likes;
import com.cuns.bce.entities.User;
import com.cuns.bce.entities.classid.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LikesRepository extends JpaRepository<Likes, LikesId>, JpaSpecificationExecutor<Likes> {
    Likes findByLikerAndLiking(User liker, User liking);
    // get count liker
    int countByLiker(User liking);
    // get count liking
    int countByLiking(User liker);
}