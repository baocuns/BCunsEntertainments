package com.cuns.bce.controllers;

import com.cuns.bce.dto.response.comics.ComicDto;
import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.entities.Chapter;
import com.cuns.bce.services.impl.ChapterService;
import com.cuns.bce.services.impl.ComicService;
import com.cuns.bce.services.impl.LikesComicService;
import com.cuns.bce.services.impl.RatingsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comics")
@Slf4j
public class ComicsController {
    final private ComicService comicService;
    final private ChapterService chapterService;
    final private RatingsComicService ratingsComicService;
    final private LikesComicService likesComicService;

    @GetMapping("")
    public String comics(@RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "24") int size,
                         Model model) {
        Page<ComicsDto> comics = comicService.findAllByPage(page, size);
        // Get count total page
        int totalPages = comics.getTotalPages();
        // Get current page
        int currentPage = comics.getNumber();
        // Send list comic
        model.addAttribute("comics", comics);
        // Send count total page
        model.addAttribute("totalPages", totalPages);
        // Send current page
        model.addAttribute("currentPage", currentPage);

        return "pages/comics/list";
    }
    @GetMapping("/{titleSlug}")
    public String details(@PathVariable String titleSlug, Model model, Principal principal) {
        ComicDto comicDto = comicService.findById(getId(titleSlug));
        model.addAttribute("comic", comicDto);

        // send boolean isLiked
        if (principal != null) {
//            model.addAttribute("isLiked", comicDto.getLike().containsValue(principal.getName()));
            model.addAttribute("isLiked", likesComicService.isLiking(principal, comicDto.getId()));
        } else {
            model.addAttribute("isLiked", false);
        }
        // is login
        model.addAttribute("isLogin", principal != null);
        // check user is rating this comic
        if (principal != null) {
            model.addAttribute("isRating", ratingsComicService.isRating(principal, comicDto.getId()));
        }
        // send count rating
        model.addAttribute("countRating", ratingsComicService.getCountRating(comicDto.getId()));

        return "pages/comics/details";
    }
    @GetMapping("/{titleSlug}/chapter/{chapterId}")
    public String chapter(@PathVariable String titleSlug, @PathVariable Long chapterId, Model model) {

        ComicDto comicDto = comicService.findById(getId(titleSlug));
        model.addAttribute("comic", comicDto);

        Chapter chapter = chapterService.findById(chapterId).get();
        model.addAttribute("chapter", chapter);

        return "pages/comics/chapter";
    }
    @PostMapping("/likes")
    public String likes(@RequestParam("titleSlug") String titleSlug, Principal principal) {
        // check principal
        if (principal != null) {
            log.info("name: " + principal.getName());
            likesComicService.addLikeAndUnlike(principal, getId(titleSlug));
        }
        return "redirect:/comics/" + titleSlug;
    }
    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
//        model.addAttribute("comics", comicService.search(keyword));
        return "pages/comics/list";
    }
    @GetMapping("/genres/{genreSlug}")
    public String genres(@PathVariable String genreSlug, Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "24") int size) {
        // get comics by genres id
        Page<ComicsDto> comics = comicService.getComicsByGenresId(getId(genreSlug), page, size);
        // Get count total page
        int totalPages = comics.getTotalPages();
        // Get current page
        int currentPage = comics.getNumber();
        // Send list comic
        model.addAttribute("comics", comics);
        // Send count total page
        model.addAttribute("totalPages", totalPages);
        // Send current page
        model.addAttribute("currentPage", currentPage);
        return "pages/comics/genres";
    }

    // ----------------- Functions -----------------
    public Long getId(String slug) {
        // Tìm vị trí của dấu gạch ngang cuối cùng trong chuỗi
        int lastIndex = slug.lastIndexOf("-");

        if (lastIndex >= 0 && lastIndex < slug.length() - 1) {
            // Trả về phần tử sau dấu gạch ngang cuối cùng
//            log.info("comics id: " + slug.substring(lastIndex + 1));
            return Long.valueOf(slug.substring(lastIndex + 1));
        } else {
            // Trường hợp không tìm thấy mã số
            return 0L;
        }
    }

}
