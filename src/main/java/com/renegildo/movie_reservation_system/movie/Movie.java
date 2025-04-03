package com.renegildo.movie_reservation_system.movie;

import com.renegildo.movie_reservation_system.genre.Genre;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String posterImageUrl;
    private Genre genre;
}
