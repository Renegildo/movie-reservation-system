package com.renegildo.movie_reservation_system.showtime;

import com.renegildo.movie_reservation_system.exception.NotFoundException;
import com.renegildo.movie_reservation_system.movie.Movie;
import com.renegildo.movie_reservation_system.movie.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(
            ShowtimeRepository showtimeRepository,
            MovieRepository movieRepository
    ) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    public Showtime create(CreateShowtimeDTO body) {
        Optional<Movie> movie = movieRepository.findById(body.getMovieId());
        if (movie.isEmpty()) throw new NotFoundException("Movie not found.");

        Showtime newShowtime = new Showtime();
        newShowtime.setTime(body.getTime());
        newShowtime.setMovie(movie.get());

        showtimeRepository.save(newShowtime);

        return newShowtime;
    }

    public List<Showtime> getAll() {
        return showtimeRepository.findAll();
    }

    public List<Showtime> getAllByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public Showtime getById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Showtime not found."));
    }
}
