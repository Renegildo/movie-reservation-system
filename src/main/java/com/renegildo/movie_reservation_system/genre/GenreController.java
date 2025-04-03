package com.renegildo.movie_reservation_system.genre;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(GenreController.baseUrl)
public class GenreController {
    public static final String baseUrl = "/genres";
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        List<Genre> genres = genreService.getAll();

        return ResponseEntity.ok(genres);
    }
}
