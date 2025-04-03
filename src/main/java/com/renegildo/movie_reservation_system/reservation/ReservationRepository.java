package com.renegildo.movie_reservation_system.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByShowtimeIdAndSeat(Long showtimeId, String seat);
    List<Reservation> findAllByShowtimeId(Long showtimeId);
}
