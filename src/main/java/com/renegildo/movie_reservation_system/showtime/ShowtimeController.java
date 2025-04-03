package com.renegildo.movie_reservation_system.showtime;

import com.renegildo.movie_reservation_system.common.UriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ShowtimeController.baseUrl)
public class ShowtimeController {
    static final String baseUrl = "/showtimes";
    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateShowtimeDTO body) {
        Showtime newShowtime = showtimeService.create(body);

        URI location = UriBuilder.buildUri(ShowtimeController.baseUrl, newShowtime.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Showtime>> getAll() {
        List<Showtime> showtimes = showtimeService.getAll();

        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<Showtime>> getAllByMovieId(@PathVariable("movieId") Long movieId) {
        List<Showtime> showtimes = showtimeService.getAllByMovieId(movieId);

        return ResponseEntity.ok(showtimes);
    }
}
