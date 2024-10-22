// Class:       Schedule
// Purpose:     Serves as top layer class for api connection
//                  Everything from restful web service will go through this class
//                  and then be directed to the lower classes to get specific information 

package com.oreillyauto.finalproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {
    
    private String date;
    
    private League league;
    
    @JsonProperty("games")
    private Game[] games;
    
    public Schedule() {
     
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Game[] getGame() {
        return games;
    }

    public void setGame(Game[] games) {
        this.games = games;
    }

}
