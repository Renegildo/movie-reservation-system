package com.renegildo.movie_reservation_system.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieDTO {
    private String title;
    private String description;
    private String posterImageUrl;
    private String genreName;
    private Long genreId;
}
