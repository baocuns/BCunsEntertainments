package com.cuns.bce.repositories;

public interface ProfileRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.Profile, java.lang.Long> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.Profile> {
}