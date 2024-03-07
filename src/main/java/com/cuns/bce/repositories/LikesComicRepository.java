package com.cuns.bce.repositories;

public interface LikesComicRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.LikesComic, com.cuns.bce.entities.classid.LikesComicId> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.LikesComic> {
}