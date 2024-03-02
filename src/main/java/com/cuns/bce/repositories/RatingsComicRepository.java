package com.cuns.bce.repositories;

public interface RatingsComicRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.RatingsComic, com.cuns.bce.entities.classid.RatingsComicId> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.RatingsComic> {
}