package com.cuns.bce.repositories;

public interface CategoryRepository extends org.springframework.data.jpa.repository.JpaRepository<com.cuns.bce.entities.Category, java.lang.Long> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.cuns.bce.entities.Category> {
}