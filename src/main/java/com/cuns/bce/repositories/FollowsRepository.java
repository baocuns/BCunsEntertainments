package com.cuns.bce.repositories;

public interface FollowsRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.Follows, com.cuns.bce.entities.FollowsId> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.Follows> {
}