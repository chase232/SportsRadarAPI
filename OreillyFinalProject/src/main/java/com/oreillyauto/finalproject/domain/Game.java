package com.oreillyauto.finalproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private String scheduled;
    private boolean conference_game;
    private Venue venue;
    //private Broadcast broadcast;
    private Home home;
    private Away away;
   
    public Game() {
        
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public boolean isConference_game() {
        return conference_game;
    }

    public void setConference_game(boolean conference_game) {
        this.conference_game = conference_game;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    
/*    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }*/

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Away getAway() {
        return away;
    }

    public void setAway(Away away) {
        this.away = away;
    }

}
