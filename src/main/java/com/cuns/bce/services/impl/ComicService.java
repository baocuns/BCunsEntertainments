package com.cuns.bce.services.impl;

import com.cuns.bce.dto.response.comics.ComicDto;
import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.entities.Chapter;
import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.User;
import com.cuns.bce.func.Funcs;
import com.cuns.bce.repositories.ComicRepository;
import com.cuns.bce.services.inter.IComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComicService implements IComicService {
    final private ComicRepository comicRepository;
    final private UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<ComicsDto> findAllByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comic> comics = comicRepository.findAll(pageable);
        return comics.map(comic -> modelMapper.map(comic, ComicsDto.class));
    }

    @Override
    public Page<Comic> findByUid(User uid, int page, int size) {
        // sắp xếp tăng dần theo id
//        Pageable pageable = PageRequest.of(page, size);
        // sắp xếp giảm dần theo id
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        // sắp xếp giảm dần theo id và tăng dần theo title
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending().and(Sort.by("title").ascending()));


        Pageable pageable = PageRequest.of(page, size);
        return comicRepository.findByUid(pageable, uid);
    }

    @Override
    public ComicDto findById(Long id) {
        Optional<Comic> comic = comicRepository.findById(id);
        // increase views by one
        comic.ifPresent(this::increaseViews);
        // Comic to ComicDto
        ComicDto comicDto = modelMapper.map(comic, ComicDto.class);
        List<Chapter> chapters = new ArrayList<>(comic.get().getChapters());

        // sort chapters by title asc (a -> z)
        chapters.sort(Comparator.comparing(chapter -> Funcs.extractNumber(chapter.getTitle())));
        // reverse chapters by title asc (z -> a)
        Collections.reverse(chapters);
        // set chapters type List to chapters in comicDto type Set
        comicDto.setChapters(new LinkedHashSet<>(chapters));

        return comicDto;
    }

    @Override
    public void increaseViews(Comic comic) {
        comic.setView(comic.getView() + 1);
        comicRepository.save(comic);
    }

    @Override
    public void likes(String username, Long comicId) {
        // get comic by id
        Optional<Comic> comic = comicRepository.findById(comicId);
        // get user by username
        Optional<User> user = userService.findByUsername(username);
        // check comic and user
        if (comic.isPresent() && user.isPresent()) {
            // get comic
            Comic comic1 = comic.get();
            // get user
            User user1 = user.get();
            // get likes
            Map<String, Object> likes = comic1.getLike();
            // check likes
            if (likes == null) {
                // create new likes
                likes = new HashMap<>();
            }
            // check likes contains key user id
            if (likes.containsValue(user1.getUsername())) {
                // remove key user id
                likes.remove(String.valueOf(user1.getId()));
            } else {
                // add key user id
                likes.put(String.valueOf(user1.getId()), user1.getUsername());
            }
            // set likes
            comic1.setLike(likes);
            // save comic
            comicRepository.save(comic1);
        }
    }

    @Override
    public List<ComicsDto> search(String title) {
        List<Comic> comics = comicRepository.findBySlugContainingIgnoreCase(Funcs.getTextSlug(title));
        return Funcs.mapList(comics);
    }
}
