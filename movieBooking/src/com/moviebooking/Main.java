package com.moviebooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MovieBookingSystem system = new MovieBookingSystem();

        // ---- Admin: Add movies ----
        Movie movie1 = new Movie("M1", "Pushpa 3", 165);
        Movie movie2 = new Movie("M2", "Jawan 2", 150);
        system.addMovie(movie1);
        system.addMovie(movie2);

        // ---- Admin: Add theatres with screens and seats ----
        City mumbai = new City("Mumbai");
        City delhi = new City("Delhi");

        List<Seat> screen1Seats = new ArrayList<>();
        screen1Seats.add(new Seat("A1", SeatType.SILVER, 150));
        screen1Seats.add(new Seat("A2", SeatType.SILVER, 150));
        screen1Seats.add(new Seat("B1", SeatType.GOLD, 250));
        screen1Seats.add(new Seat("B2", SeatType.GOLD, 250));
        screen1Seats.add(new Seat("C1", SeatType.PLATINUM, 400));
        Screen screen1 = new Screen("S1", "Screen 1", screen1Seats);

        List<Seat> screen2Seats = new ArrayList<>();
        screen2Seats.add(new Seat("A1", SeatType.SILVER, 120));
        screen2Seats.add(new Seat("A2", SeatType.SILVER, 120));
        screen2Seats.add(new Seat("B1", SeatType.GOLD, 200));
        Screen screen2 = new Screen("S2", "Screen 2", screen2Seats);

        Theatre pvr = new Theatre("T1", "PVR Juhu", mumbai, Arrays.asList(screen1));
        Theatre inox = new Theatre("T2", "INOX CP", delhi, Arrays.asList(screen2));
        system.addTheatre(pvr);
        system.addTheatre(inox);

        // ---- Admin: Add shows ----
        Show show1 = new Show("SH1", movie1, screen1, "10:00 AM");
        Show show2 = new Show("SH2", movie2, screen1, "02:00 PM");
        Show show3 = new Show("SH3", movie1, screen2, "06:00 PM");
        system.addShow(show1);
        system.addShow(show2);
        system.addShow(show3);

        // ---- Admin: Set pricing rules ----
        system.addPricingRule(new DemandPricingRule(0.6, 1.5));
        system.addPricingRule(new WeekdayPricingRule(1.2));
        system.addPricingRule(new ShowTimePricingRule("04:00 PM", "10:00 PM", 1.3));

        // ---- Register users (unique email) ----
        User user1 = system.registerUser("U1", "Anisha", "anisha@email.com");
        User user2 = system.registerUser("U2", "Raj", "raj@email.com");
        system.registerUser("U3", "Duplicate", "anisha@email.com"); // should fail

        System.out.println("\n=== Movie Ticket Booking System ===\n");

        // ---- Flow 1: User browses by city → movies ----
        System.out.println("--- Movies in Mumbai ---");
        for (Movie m : system.showMovies("Mumbai")) {
            System.out.println("  " + m.getTitle());
        }

        // ---- User picks a movie → sees theatres and slots ----
        System.out.println("\n--- Shows for Pushpa 3 in Mumbai ---");
        for (Show s : system.showShowsForMovie("M1", "Mumbai")) {
            System.out.println("  " + s.getScreen().getName() + " at " + s.getStartTime());
        }

        // ---- Flow 2: User browses by city → theatres ----
        System.out.println("\n--- Theatres in Mumbai ---");
        for (Theatre t : system.showTheatres("Mumbai")) {
            System.out.println("  " + t.getName());
        }

        // ---- User picks a theatre → sees movies and slots ----
        System.out.println("\n--- Shows in PVR Juhu ---");
        for (Show s : system.showShowsInTheatre("T1")) {
            System.out.println("  " + s.getMovie().getTitle() + " at " + s.getStartTime());
        }

        // ---- User picks a show → sees seat map ----
        System.out.println("\n--- Available seats for SH1 ---");
        Map<String, Boolean> seatMap = system.showAvailableSeats("SH1");
        for (Map.Entry<String, Boolean> entry : seatMap.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + (entry.getValue() ? "AVAILABLE" : "TAKEN"));
        }

        // ---- Check dynamic pricing ----
        System.out.println("\n--- Prices for SH1 ---");
        System.out.println("  A1 (Silver): " + system.getPrice("SH1", "A1"));
        System.out.println("  B1 (Gold): " + system.getPrice("SH1", "B1"));
        System.out.println("  C1 (Platinum): " + system.getPrice("SH1", "C1"));

        // ---- Booking flow: hold → pay → ticket ----
        System.out.println("\n--- Booking: Hold seats ---");
        SeatHold hold1 = system.holdSeats("SH1", Arrays.asList("A1", "B1"), user1.getId());

        System.out.println("\n--- Another user tries same seats (should fail) ---");
        SeatHold hold2 = system.holdSeats("SH1", Arrays.asList("A1"), user2.getId());

        System.out.println("\n--- Confirm booking with UPI ---");
        MovieTicket ticket1 = system.confirmBooking(hold1.getHoldId(), PaymentMode.UPI);

        // ---- Cancellation with refund to original payment mode ----
        System.out.println("\n--- Cancel ticket (refund to UPI) ---");
        system.cancelTicket(ticket1);

        // ---- Seats available again after cancellation ----
        System.out.println("\n--- Rebooking after cancellation ---");
        SeatHold hold3 = system.holdSeats("SH1", Arrays.asList("A1"), user2.getId());
        MovieTicket ticket2 = system.confirmBooking(hold3.getHoldId(), PaymentMode.CARD);

        // ---- Concurrent booking test ----
        System.out.println("\n--- Concurrent booking test ---");
        Thread t1 = new Thread(() -> {
            SeatHold h = system.holdSeats("SH2", Arrays.asList("A1", "A2"), "U1");
            if (h != null) system.confirmBooking(h.getHoldId(), PaymentMode.UPI);
        });
        Thread t2 = new Thread(() -> {
            SeatHold h = system.holdSeats("SH2", Arrays.asList("A1", "B1"), "U2");
            if (h != null) system.confirmBooking(h.getHoldId(), PaymentMode.CARD);
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
