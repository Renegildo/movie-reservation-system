package com.renegildo.movie_reservation_system.reservation;

import com.renegildo.movie_reservation_system.common.UriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ReservationController.baseUrl)
public class ReservationController {
    public static final String baseUrl = "/reservations";
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody CreateReservationDTO body,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Reservation newReservation = reservationService.create(body, userDetails);

        URI location = UriBuilder.buildUri(ReservationController.baseUrl, newReservation.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Reservation reservation = reservationService.getReservationByIdAndUserDetails(id, userDetails);

        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/available/{showtimeId}")
    public ResponseEntity<List<String>> getAvailableSeats(@PathVariable("showtimeId") Long showtimeId) {
        List<String> availableSeats = reservationService.getAllAvailableSeats(showtimeId);

        return ResponseEntity.ok(availableSeats);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        reservationService.cancelReservation(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
