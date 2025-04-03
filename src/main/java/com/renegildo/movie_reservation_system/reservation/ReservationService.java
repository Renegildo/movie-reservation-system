package com.renegildo.movie_reservation_system.reservation;

import com.renegildo.movie_reservation_system.exception.NotFoundException;
import com.renegildo.movie_reservation_system.exception.UserDoesNotOwnReservation;
import com.renegildo.movie_reservation_system.showtime.Showtime;
import com.renegildo.movie_reservation_system.showtime.ShowtimeService;
import com.renegildo.movie_reservation_system.user.User;
import com.renegildo.movie_reservation_system.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShowtimeService showtimeService;
    private final UserService userService;
    private final ReservationValidator reservationValidator;

    public ReservationService(
            ReservationRepository reservationRepository,
            ShowtimeService showtimeService,
            UserService userService,
            ReservationValidator reservationValidator
    ) {
        this.reservationRepository = reservationRepository;
        this.showtimeService = showtimeService;
        this.userService = userService;
        this.reservationValidator = reservationValidator;
    }

    public Reservation create(CreateReservationDTO body, UserDetails userDetails) {
        Showtime showtime = showtimeService.getById(body.getShowtimeId());
        reservationValidator.validateSeat(body.getSeat(), body.getShowtimeId());

        User user = userService.getByEmail(userDetails.getUsername());

        Reservation newReservation = new Reservation();
        newReservation.setSeat(body.getSeat());
        newReservation.setUser(user);
        newReservation.setShowtime(showtime);

        reservationRepository.save(newReservation);

        return newReservation;
    }

    public Reservation getReservationByIdAndUserDetails(Long id, UserDetails userDetails) throws RuntimeException {
        User currentUser = userService.getByEmail(userDetails.getUsername());

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found."));

        boolean userOwnsReservation = reservation.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
        if (!userOwnsReservation || !isAdmin)
            throw new UserDoesNotOwnReservation("User do not owns reservation.");

        return reservation;
    }

    public List<String> getAllAvailableSeats(Long showtimeId) {
        showtimeService.getById(showtimeId);
        return reservationValidator.getAllAvailableSeatsByShowtimeId(showtimeId);
    }
}
