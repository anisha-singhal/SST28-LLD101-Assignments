package com.moviebooking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowService {
    private List<Show> shows;
    private final ReentrantLock showLock;

    public ShowService() {
        this.shows = new ArrayList<>();
        this.showLock = new ReentrantLock();
    }

    public void addShow(Show show) {
        showLock.lock();
        try {
            shows.add(show);
            System.out.println("Show added: " + show.getMovie().getTitle() + " at " + show.getStartTime());
        } finally {
            showLock.unlock();
        }
    }

    public List<Show> getShowsForMovie(String movieId) {
        List<Show> result = new ArrayList<>();
        for (Show show : shows) {
            if (show.getMovie().getId().equals(movieId)) {
                result.add(show);
            }
        }
        return result;
    }

    public List<Show> getAllShows() { return shows; }
}
