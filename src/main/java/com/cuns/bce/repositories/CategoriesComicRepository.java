package com.cuns.bce.repositories;

import com.cuns.bce.entities.CategoriesComic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriesComicRepository extends JpaRepository<CategoriesComic, Long>, JpaSpecificationExecutor<CategoriesComic> {
    // get all comic by category id
    Page<CategoriesComic> findByCategoryId(Long categoryId, Pageable pageable);
}