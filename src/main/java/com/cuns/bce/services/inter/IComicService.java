package com.cuns.bce.services.inter;

import com.cuns.bce.dto.response.comics.ComicDto;
import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IComicService {
    Page<ComicsDto> findAllByPage(int page, int size);
    Page<Comic> findByUid(User uid, int page, int size);
    ComicDto findById(Long id);
    Comic findById(Long id, boolean isComic);
    // increase views by one
    void increaseViews(Comic comic);
    // like save uid type {"1": "abc"} or {"1": "abc", "2": "xyz"}
    void likes(String username, Long comicId);
    // search by title
    List<ComicsDto> search(String title);
    // get genres by comic id
    Page<ComicsDto> getComicsByGenresId(Long genresId, int page, int size);
}
