package com.moviebooking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieBookingSystem {
    private List<Theatre> theatres;
    private ShowService showService;
    private BookingService bookingService;

    public MovieBookingSystem() {
        this.theatres = new ArrayList<>();
        this.showService = new ShowService();
        this.bookingService = new BookingService();
    }

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    public void addShow(Show show) {
        showService.addShow(show);
    }

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

    public MovieTicket bookTickets(String showId, List<String> seats) {
        for (Show show : showService.getAllShows()) {
            if (show.getId().equals(showId)) {
                return bookingService.bookTickets(show, seats);
            }
        }
        System.out.println("Show not found: " + showId);
        return null;
    }

    public Payment cancelTicket(MovieTicket ticket) {
        return bookingService.cancelTicket(ticket);
    }

    public ShowService getShowService() { return showService; }
    public BookingService getBookingService() { return bookingService; }
}
