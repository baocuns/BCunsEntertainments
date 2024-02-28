package com.cuns.bce.repositories;

public interface LikesRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.Likes, com.cuns.bce.entities.classid.LikesId> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.Likes> {
}