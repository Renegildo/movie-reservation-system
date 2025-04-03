package com.renegildo.movie_reservation_system.reservation;

import lombok.Data;

@Data
public class CreateReservationDTO {
    private String seat;
    private Long showtimeId;
}
