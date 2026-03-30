package com.moviebooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MovieBookingSystem system = new MovieBookingSystem();

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

        Movie movie1 = new Movie("M1", "Pushpa 3", 165);
        Movie movie2 = new Movie("M2", "Jawan 2", 150);

        Show show1 = new Show("SH1", movie1, screen1, "10:00 AM");
        Show show2 = new Show("SH2", movie2, screen1, "02:00 PM");
        Show show3 = new Show("SH3", movie1, screen2, "06:00 PM");

        system.addShow(show1);
        system.addShow(show2);
        system.addShow(show3);

        System.out.println("=== Movie Ticket Booking System ===\n");

        System.out.println("--- Theatres in Mumbai ---");
        for (Theatre t : system.showTheatres("Mumbai")) {
            System.out.println("  " + t.getName());
        }

        System.out.println("\n--- Movies in Delhi ---");
        for (Movie m : system.showMovies("Delhi")) {
            System.out.println("  " + m.getTitle());
        }

        System.out.println("\n--- Booking tickets ---");
        MovieTicket ticket1 = system.bookTickets("SH1", Arrays.asList("A1", "B1"));
        MovieTicket ticket2 = system.bookTickets("SH1", Arrays.asList("A1"));

        System.out.println("\n--- Cancellation with refund ---");
        system.cancelTicket(ticket1);

        System.out.println("\n--- Rebooking after cancellation ---");
        MovieTicket ticket3 = system.bookTickets("SH1", Arrays.asList("A1"));

        System.out.println("\n--- Concurrent booking test ---");
        Thread t1 = new Thread(() -> system.bookTickets("SH2", Arrays.asList("A1", "A2")));
        Thread t2 = new Thread(() -> system.bookTickets("SH2", Arrays.asList("A1", "B1")));
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
