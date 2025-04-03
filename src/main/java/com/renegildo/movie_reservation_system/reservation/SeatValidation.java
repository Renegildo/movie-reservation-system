package com.renegildo.movie_reservation_system.reservation;

import java.util.ArrayList;
import java.util.List;

public class SeatValidation {
    private static final String seatRegex = "^[a-z]\\d{2}$";
    private static final Integer numbersSeat = 15;
    private static final String letters = "abcdef";

    public static boolean isValidSeat(String seat) {
        return seat.toLowerCase().matches(SeatValidation.seatRegex);
    }

    public static List<String> getAllPossibleSeats() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < SeatValidation.letters.length(); i++) {
            for (int j = 0; j < SeatValidation.numbersSeat; j++) {
                String seatString = SeatValidation.letters.charAt(i) + String.format("%02d", j);
                result.add(seatString);
            }
        }

        return result;
    }
}
