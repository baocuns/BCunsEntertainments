package com.cuns.bce.api;

import com.cuns.bce.dto.response.comics.ComicsDto;
import com.cuns.bce.services.impl.ComicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/comics")
@RequiredArgsConstructor
public class ComicsApi {
    final private ComicService comicService;
    @PostMapping("/search")
    public ResponseEntity<List<ComicsDto>> search(@RequestParam String title) {
        try {
            List<ComicsDto> comics = comicService.search(title);
            return ResponseEntity.ok(comics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
