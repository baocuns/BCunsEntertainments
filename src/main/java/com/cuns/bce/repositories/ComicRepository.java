package com.cuns.bce.repositories;

import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Long>, JpaSpecificationExecutor<Comic> {
    Page<Comic> findByUid(Pageable pageable, User uid);
    List<Comic> findByTitleContainingIgnoreCase(String title);
    List<Comic> findBySlugContainingIgnoreCase(String titleSlug);
}