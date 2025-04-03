package com.renegildo.movie_reservation_system.movie;

import com.renegildo.movie_reservation_system.exception.NotFoundException;
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

    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found."));
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

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found."));

        movieRepository.delete(movie);
    }

    public Movie updateMovie(Long id, UpdateMovieDTO body) {
        Movie movie = getById(id);

        if (body.getTitle() != null) movie.setTitle(body.getTitle());
        if (body.getDescription() != null) movie.setDescription(body.getDescription());
        if (body.getPosterImageUrl() != null) movie.setPosterImageUrl(body.getPosterImageUrl());

        if (body.getGenreId() != null) {
            Genre genre = genreService.getById(body.getGenreId());
            movie.setGenre(genre);
        } else if (body.getGenreName() != null) {
            Genre genre = genreService.getByName(body.getGenreName());
            movie.setGenre(genre);
        }

        return movieRepository.save(movie);
    }
}
