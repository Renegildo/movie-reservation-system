package com.renegildo.movie_reservation_system.reservation;

import com.renegildo.movie_reservation_system.showtime.Showtime;
import com.renegildo.movie_reservation_system.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String seat;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "showtime_id", referencedColumnName = "id")
    private Showtime showtime;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;
}
