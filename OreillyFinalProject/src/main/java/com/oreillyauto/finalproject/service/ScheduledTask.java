// Class:       ScheduledTask
// Purpose:     This class handles setting the initial date, any new date input by the user,
//                  and the initial connection to the restful web service. It also saves all data
//                  to the database

package com.oreillyauto.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.oreillyauto.finalproject.dao.WidgetChildRepository;
import com.oreillyauto.finalproject.dao.WidgetParentRepository;
import com.oreillyauto.finalproject.domain.Game;
import com.oreillyauto.finalproject.domain.League;
import com.oreillyauto.finalproject.domain.Schedule;
import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.domain.WidgetProperty;

@Component
public class ScheduledTask {

    @Autowired
    WidgetParentRepository parentRepo;
    @Autowired
    WidgetChildRepository childRepo;
    
    List<String> gameList = new ArrayList<>();
    List<String> gameListMonth = new ArrayList<>();

    public void setInitialDate() {
        
        // Used to see if date is already in database to avoid duplicate entries
        int checkDate = 0; 
        for (String game : gameList) {
            if(game.equals("2018/12/16")) {
                return;
            } else {
                checkDate++;
            }
        }
        
        if (checkDate == gameList.size()) {
            gameList.add("2018/12/16");
        }
        
        String date = "2018/12/16";
        gameList.add(date);

        // OLD KEY
        //String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/2018/12/16/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        String serviceUri = "http://api.sportradar.us/nba/trial/v5/en/games/2018/12/16/schedule.json?api_key=nbwhs2wr7ktmcagxyh9brpru";
        RestTemplate restTemplate = new RestTemplate();

        // Call the service and populate the Response Object
        Schedule responseJSON = restTemplate.getForObject(serviceUri, Schedule.class);

        Game[] gameArray = responseJSON.getGame();
        League league = responseJSON.getLeague();

        for (Game game : gameArray) {
            
            String newDate = date.replace("/", "-");
            newDate = newDate + " 0:00:00.000";

            Widget widget = new Widget();
            widget.setDateTime(newDate);
            widget.setEventType(league.getName());
            widget.setSmsSent("N");

            WidgetProperty wp = new WidgetProperty();
            wp.setWidget(widget);
            wp.setEventKey(game.getVenue().getName());
            wp.setEventValue(game.getAway().getName() + " at " + game.getHome().getName());

            parentRepo.save(widget);
            childRepo.save(wp);
        }
    }

    public void setUserDate(String userDate) {

        // Issue with dates on the first of the month so a switch statement fixes it 
        userDate = changeDateIfNeeded(userDate);
    
        // Used to see if date is already in database to avoid duplicate entries
        int checkDate = 0; 
        for (String game : gameList) {
            if(game.equals(userDate)) {
                return;
            } else {
                checkDate++;
            }
        }
        
        if (checkDate == gameList.size()) {
            gameList.add(userDate);
        }
        
        // OLD KEY
        //String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/" + userDate + "/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        String serviceUri = "http://api.sportradar.us/nba/trial/v5/en/games/" + userDate + "/schedule.json?api_key=nbwhs2wr7ktmcagxyh9brpru";
        RestTemplate restTemplate = new RestTemplate();

        // Call the service and populate the Response Object
        Schedule responseJSON = restTemplate.getForObject(serviceUri, Schedule.class);

        Game[] gameArray = responseJSON.getGame();
        League league = responseJSON.getLeague();

        for (Game game : gameArray) {

            String newDate = userDate.replace("/", "-");
            newDate = newDate + " 0:00:00.000";

            Widget widget = new Widget();
            widget.setDateTime(newDate);
            widget.setEventType(league.getName());
            widget.setSmsSent("N");

            WidgetProperty wp = new WidgetProperty();
            wp.setWidget(widget);
            wp.setEventKey(game.getVenue().getName());
            wp.setEventValue(game.getAway().getName() + " at " + game.getHome().getName());

            parentRepo.save(widget);
            childRepo.save(wp);
        }
    }
    
    public String changeDateIfNeeded(String userDate) {
        switch (userDate) {
            case "2018/7/32":
                return "2018/08/01";
            case "2018/8/32":
                return "2018/09/01";
            case "2018/9/31":
                return "2018/10/01";
            case "2018/10/32":
                return "2018/11/01";
            case "2018/11/31":
                return "2018/12/01";
            case "2018/12/32":
                return "2019/01/01";
            case "2019/1/32":
                return "2019/02/01";
            case "2019/2/29":
                return "2019/03/01";
            case "2019/3/32":
                return "2019/04/01";
            case "2019/4/31":
                return "2019/05/01";
            case "2019/5/32":
                return "2019/06/01";
            case "2019/6/31":
                return "2019/07/01";
        }
        return userDate;
    }
}



