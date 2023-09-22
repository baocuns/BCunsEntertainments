package com.cuns.bce.services.impl;

import com.cuns.bce.entities.Chapter;
import com.cuns.bce.entities.Photo;
import com.cuns.bce.repositories.ChapterRepository;
import com.cuns.bce.services.inter.IChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService implements IChapterService {
    final private ChapterRepository chapterRepository;

    @Override
    public Optional<Chapter> findById(Long id) {
        Optional<Chapter> chapter = chapterRepository.findById(id);
        // increase views by one
        chapter.ifPresent(this::increaseViews);

        // sort photos by title asc (a -> z) in chapter: cần sửa lại phần này vì nó sẽ sort theo số thứ tự của hình ảnh.
        Set<Photo> sortPhotos = new TreeSet<>(Comparator.comparing(photo ->
                Integer.valueOf(
                        photo.getTitle().split(" ")[photo.getTitle().split(" ").length - 1]
                )));
        // add all photos to sortPhotos
        sortPhotos.addAll(chapter.get().getPhotos());
        // set photos to chapter
        chapter.get().setPhotos(sortPhotos);
        return chapter;
    }

    @Override
    public void increaseViews(Chapter chapter) {
        chapter.setView(chapter.getView() + 1);
        chapterRepository.save(chapter);
    }
}
