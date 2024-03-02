package com.cuns.bce.controllers;

import com.cuns.bce.services.impl.RatingsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ratings")
@Slf4j
public class RatingsController {
    final private RatingsComicService ratingsComicService;

    // --- comics ---
    @PostMapping("/comic/add")
    public String addRating(@RequestParam Long comicId,
                            @RequestParam String content,
                            @RequestParam Integer rate,
                            Principal principal) {
        // check if user is login
        if (principal == null) {
            return "redirect:/login";
        } else {
            // add rating to comic
            ratingsComicService.addRating(principal, comicId, content, rate);
        }
//        log.info("comicId: " + comicId + " content: " + content + " rate: " + rate);
        return "redirect:/comics";
    }
}
