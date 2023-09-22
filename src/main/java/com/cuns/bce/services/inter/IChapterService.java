package com.cuns.bce.services.inter;

import com.cuns.bce.entities.Chapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IChapterService {
    Optional<Chapter> findById(Long id);
    // increase views by one
    void increaseViews(Chapter chapter);
}
