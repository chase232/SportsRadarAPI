package com.oreillyauto.finalproject.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final Instant now = Instant.now();
    private final String startTimeString = now.toString();

    @Autowired
    WidgetParentRepository parentRepo;
    @Autowired
    WidgetChildRepository childRepo;

    @Scheduled(fixedRate = 30000)    //   <=== This method will run every 30 seconds
    public void gatherFooData() {   
        System.out.println("Got here");
        
     // EXAMPLE SCHEDULED TASK STRATEGY
        String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/2018/12/15/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        RestTemplate restTemplate = new RestTemplate();
        
        // Call the service and populate the Response Object
        Schedule responseJSON = restTemplate.getForObject(serviceUri, Schedule.class); 
        //Schedule[] schedule = responseJSON.getSchedule(); // Where “Features” is a custom array of data 
        Game[] gameArray = responseJSON.getGame();
        League league = responseJSON.getLeague();
 
        for (Game game : gameArray) {
            
            System.out.println(league.getName());
            System.out.println(game.getId());
            System.out.println("Home Team: " + game.getHome().getAlias() + "  " + game.getHome().getName());
            System.out.println("Away Team: " + game.getAway().getAlias() + "  " + game.getAway().getName());
            System.out.println("Location: " + game.getVenue().getName() + "  " + game.getVenue().getState());
            System.out.println("Date: " + game.getScheduled());
            System.out.println("");
            
            String date = game.getScheduled();
            String newDate = date.substring(0, 10);
            newDate = newDate + " 12:00:00.000";
            System.out.println(newDate);
            
            Widget widget = new Widget();
            widget.setDateTime(newDate);
            widget.setEventType(league.getName());
            widget.setSmsSent("N");
            
            WidgetProperty wp = new WidgetProperty();
            wp.setWidget(widget);
            wp.setEventKey(game.getVenue().getName() + "  " + game.getVenue().getState());
            wp.setEventValue(game.getHome().getAlias() + "  " + game.getHome().getName()
                             + game.getAway().getAlias() + "  " + game.getAway().getName());
            
            parentRepo.save(widget);
            childRepo.save(wp);
            
            //Build an instance of the parent object
            // Populate parent object values and call parentRepo.save(parentObj) (Saving over and over again with the same data is fine)
            // The return object from save should be an instance of the object saved - which contains the generated key (if one was created)

            // Build an instance of the child object
            // Populate child object values and call childRepo.save(childObj)
        }
    }
}

