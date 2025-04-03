package com.renegildo.movie_reservation_system.exception;

public class UserDoesNotOwnReservation extends RuntimeException {
    public UserDoesNotOwnReservation(String message) {
        super(message);
    }
}
