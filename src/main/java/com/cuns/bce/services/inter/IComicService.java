package com.cuns.bce.services.inter;

import com.cuns.bce.dto.response.api.RAComicSearchDto;
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
    // search by title
    List<RAComicSearchDto> search(String title);
    // get genres by comic id
    Page<ComicsDto> getComicsByGenresId(Long genresId, int page, int size);
}
