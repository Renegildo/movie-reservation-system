package com.renegildo.movie_reservation_system.movie;

import com.renegildo.movie_reservation_system.genre.Genre;
import com.renegildo.movie_reservation_system.genre.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreService genreService;

    public MovieService(
            MovieRepository movieRepository,
            GenreService genreService
    ) {
        this.movieRepository = movieRepository;
        this.genreService = genreService;
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Movie create(CreateMovieDTO body) {
        Movie newMovie = new Movie();

        Genre movieGenre = null;
        if (body.getGenreId() != null) {
            movieGenre = genreService.getById(body.getGenreId());
        } else if (body.getGenreName() != null) {
            movieGenre = genreService.getByName(body.getGenreName());
        }

        newMovie.setTitle(body.getTitle());
        newMovie.setDescription(body.getDescription());
        newMovie.setPosterImageUrl(body.getPosterImageUrl());
        newMovie.setGenre(movieGenre);

        movieRepository.save(newMovie);

        return newMovie;
    }
}
