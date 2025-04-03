package com.renegildo.movie_reservation_system.genre;

import com.renegildo.movie_reservation_system.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre getByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Genre not found."));
    }

    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found."));
    }
}
