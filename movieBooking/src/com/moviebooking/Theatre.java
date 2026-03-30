package com.moviebooking;

import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private City city;
    private List<Screen> screens;

    public Theatre(String id, String name, City city, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public City getCity() { return city; }
    public List<Screen> getScreens() { return screens; }
}
