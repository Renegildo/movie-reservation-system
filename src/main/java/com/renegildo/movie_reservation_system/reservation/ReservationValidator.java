package com.renegildo.movie_reservation_system.reservation;

import com.renegildo.movie_reservation_system.exception.InvalidSeatException;
import com.renegildo.movie_reservation_system.exception.SeatAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ReservationValidator {
    private final ReservationRepository reservationRepository;

    public ReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validateSeat(String seat, Long showtimeId) {
        boolean seatIsInvalid = !SeatValidation.isValidSeat(seat);
        if (seatIsInvalid)
            throw new InvalidSeatException("Invalid seat.");

        boolean seatIsAlreadyTaken = reservationRepository.findByShowtimeIdAndSeat(showtimeId, seat).isPresent();
        if (seatIsAlreadyTaken)
            throw new SeatAlreadyTakenException("Seat already taken.");
    }

    public List<String> getAllAvailableSeatsByShowtimeId(Long showtimeId) {
        List<Reservation> showtimeReservations = this.reservationRepository.findAllByShowtimeId(showtimeId);
        Set<String> possibleSeats = new HashSet<>(SeatValidation.getAllPossibleSeats());
        showtimeReservations.forEach(reservation -> possibleSeats.remove(reservation.getSeat()));

        return new ArrayList<>(possibleSeats);
    }
}
