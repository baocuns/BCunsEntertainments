package com.cuns.bce.api;

import com.cuns.bce.dto.response.api.RARatingsComicDto;
import com.cuns.bce.services.impl.RatingsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
@Slf4j
public class RatingsApi {
    final private RatingsComicService ratingsComicService;

    // --- comics ---
    @PostMapping("/comic/all")
    public ResponseEntity<List<RARatingsComicDto>> getUsersRatings(@RequestParam Long comicId) {
        // get all user rating to comic
        List<RARatingsComicDto> ratings = ratingsComicService.getAllRatingsComicByComicId(comicId);
        return ResponseEntity.ok(ratings);
    }

}
