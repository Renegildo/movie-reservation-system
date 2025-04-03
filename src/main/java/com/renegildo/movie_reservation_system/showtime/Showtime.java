package com.renegildo.movie_reservation_system.showtime;

import com.renegildo.movie_reservation_system.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Data
public class Showtime {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;
    private LocalDateTime time;

    public void setTime(LocalDateTime time) {
        this.time = time.atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
