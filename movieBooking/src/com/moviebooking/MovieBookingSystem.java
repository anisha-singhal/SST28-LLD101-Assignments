package com.moviebooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MovieBookingSystem {
    private List<Theatre> theatres;
    private List<Movie> movies;
    private Map<String, User> usersByEmail;
    private ShowService showService;
    private BookingService bookingService;

    public MovieBookingSystem() {
        this.theatres = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.usersByEmail = new HashMap<>();
        this.showService = new ShowService();
        this.bookingService = new BookingService();
    }

    // ---- Admin APIs ----

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        System.out.println("Movie added: " + movie.getTitle());
    }

    public void addShow(Show show) {
        showService.addShow(show);
    }

    public void addPricingRule(PricingRule rule) {
        bookingService.addPricingRule(rule);
    }

    public void removePricingRule(PricingRule rule) {
        bookingService.removePricingRule(rule);
    }

    // ---- User Management ----

    public User registerUser(String id, String name, String email) {
        if (usersByEmail.containsKey(email)) {
            System.out.println("User with email " + email + " already exists.");
            return usersByEmail.get(email);
        }
        User user = new User(id, name, email);
        usersByEmail.put(email, user);
        System.out.println("User registered: " + name + " (" + email + ")");
        return user;
    }

    // ---- Browse APIs ----

    public List<Theatre> showTheatres(String cityName) {
        List<Theatre> result = new ArrayList<>();
        for (Theatre theatre : theatres) {
            if (theatre.getCity().getName().equalsIgnoreCase(cityName)) {
                result.add(theatre);
            }
        }
        return result;
    }

    public List<Movie> showMovies(String cityName) {
        Set<String> seen = new HashSet<>();
        List<Movie> result = new ArrayList<>();

        List<Theatre> cityTheatres = showTheatres(cityName);
        for (Theatre theatre : cityTheatres) {
            for (Screen screen : theatre.getScreens()) {
                for (Show show : showService.getAllShows()) {
                    if (show.getScreen().getId().equals(screen.getId())) {
                        if (!seen.contains(show.getMovie().getId())) {
                            seen.add(show.getMovie().getId());
                            result.add(show.getMovie());
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<Show> showShowsForMovie(String movieId, String cityName) {
        List<Show> result = new ArrayList<>();
        List<Theatre> cityTheatres = showTheatres(cityName);

        Set<String> cityScreenIds = new HashSet<>();
        for (Theatre theatre : cityTheatres) {
            for (Screen screen : theatre.getScreens()) {
                cityScreenIds.add(screen.getId());
            }
        }

        for (Show show : showService.getAllShows()) {
            if (show.getMovie().getId().equals(movieId)
                    && cityScreenIds.contains(show.getScreen().getId())) {
                result.add(show);
            }
        }
        return result;
    }

    public List<Show> showShowsInTheatre(String theatreId) {
        List<Show> result = new ArrayList<>();

        Theatre theatre = null;
        for (Theatre t : theatres) {
            if (t.getId().equals(theatreId)) {
                theatre = t;
                break;
            }
        }
        if (theatre == null) return result;

        Set<String> screenIds = new HashSet<>();
        for (Screen screen : theatre.getScreens()) {
            screenIds.add(screen.getId());
        }

        for (Show show : showService.getAllShows()) {
            if (screenIds.contains(show.getScreen().getId())) {
                result.add(show);
            }
        }
        return result;
    }

    public Map<String, Boolean> showAvailableSeats(String showId) {
        for (Show show : showService.getAllShows()) {
            if (show.getId().equals(showId)) {
                Map<String, Boolean> availability = new HashMap<>();
                for (Seat seat : show.getScreen().getSeats()) {
                    availability.put(seat.getSeatNumber(), show.isSeatAvailable(seat.getSeatNumber()));
                }
                return availability;
            }
        }
        return new HashMap<>();
    }

    // ---- Booking APIs ----

    public SeatHold holdSeats(String showId, List<String> seats, String userId) {
        for (Show show : showService.getAllShows()) {
            if (show.getId().equals(showId)) {
                return bookingService.holdSeats(show, seats, userId);
            }
        }
        System.out.println("Show not found: " + showId);
        return null;
    }

    public MovieTicket confirmBooking(String holdId, PaymentMode paymentMode) {
        return bookingService.confirmBooking(holdId, paymentMode);
    }

    public Payment cancelTicket(MovieTicket ticket) {
        return bookingService.cancelTicket(ticket);
    }

    public double getPrice(String showId, String seatNumber) {
        for (Show show : showService.getAllShows()) {
            if (show.getId().equals(showId)) {
                Seat seat = show.getSeatByNumber(seatNumber);
                if (seat != null) {
                    return bookingService.calculatePrice(show, seat);
                }
            }
        }
        return 0;
    }

    public ShowService getShowService() { return showService; }
    public BookingService getBookingService() { return bookingService; }
}
