package com.renegildo.movie_reservation_system.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieDTO {
    private String title;
    private String description;
    private String posterImageUrl;
    private Long genreId;
    private String genreName;
}
