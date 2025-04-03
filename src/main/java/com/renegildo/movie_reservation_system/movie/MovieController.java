package com.renegildo.movie_reservation_system.movie;

import com.renegildo.movie_reservation_system.common.UriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(MovieController.baseUrl)
public class MovieController {
    public static final String baseUrl = "/movies";
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        List<Movie> movies = movieService.getAll();

        return ResponseEntity.ok(movies);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateMovieDTO body) {
        Movie newMovie = movieService.create(body);

        URI location = UriBuilder.buildUri(MovieController.baseUrl, newMovie.getId());

        return ResponseEntity.created(location).build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @RequestBody UpdateMovieDTO body
    ) {
        Movie updatedMovie = movieService.updateMovie(id, body);

        return ResponseEntity.ok(updatedMovie);
    }
}
