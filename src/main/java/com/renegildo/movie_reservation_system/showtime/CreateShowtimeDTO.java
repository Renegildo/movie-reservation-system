package com.renegildo.movie_reservation_system.showtime;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateShowtimeDTO {
    private LocalDateTime time;
    private Long movieId;
}
