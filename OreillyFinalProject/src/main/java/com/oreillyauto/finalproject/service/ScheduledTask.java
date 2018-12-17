package com.oreillyauto.finalproject.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.impl.sql.catalog.SYSROUTINEPERMSRowFactory;
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
    private final Instant now = Instant.now();
    private final String startTimeString = now.toString();

    @Autowired
    WidgetParentRepository parentRepo;
    @Autowired
    WidgetChildRepository childRepo;
    
    List<String> gameList = new ArrayList<>();
    List<String> gameListMonth = new ArrayList<>();

    //@Scheduled(fixedRate = 30000)    //   <=== This method will run every 30 seconds
    public void setInitialDate() {
        String date = "2018/12/16";
        gameList.add(date);
        System.out.println("Got here");

        //String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/2018/12/16/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        String serviceUri = "http://api.sportradar.us/nba/trial/v5/en/games/2018/12/16/schedule.json?api_key=nbwhs2wr7ktmcagxyh9brpru";
        RestTemplate restTemplate = new RestTemplate();

        // Call the service and populate the Response Object
      // EXAMPLE SCHEDULED TASK STRATEGY
        Schedule responseJSON = restTemplate.getForObject(serviceUri, Schedule.class);
        //Schedule[] schedule = responseJSON.getSchedule(); // Where “Features” is a custom array of data 

        Game[] gameArray = responseJSON.getGame();
        League league = responseJSON.getLeague();

        for (Game game : gameArray) {

            /*System.out.println(league.getName());
            System.out.println(game.getId());
            System.out.println("Home Team: " + game.getHome().getAlias() + "  " + game.getHome().getName());
            System.out.println("Away Team: " + game.getAway().getAlias() + "  " + game.getAway().getName());
            System.out.println("Location: " + game.getVenue().getName() + "  " + game.getVenue().getState());
            System.out.println("Date: " + game.getScheduled());
            System.out.println("");*/

            //String date = game.getScheduled();
            //String newDate = date.substring(0, 10);
            //newDate = newDate + " 0:00:00.000";
            //ystem.out.println(newDate);
            
            String newDate = date.replace("/", "-");
            //String date = game.getScheduled();
            //newDate = date.substring(0, 10);
            newDate = newDate + " 0:00:00.000";
            //System.out.println(newDate);

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

            //Build an instance of the parent object
            // Populate parent object values and call parentRepo.save(parentObj) (Saving over and over again with the same data is fine)
            // The return object from save should be an instance of the object saved - which contains the generated key (if one was created)

            // Build an instance of the child object
            // Populate child object values and call childRepo.save(childObj)
        }
    }

    public void setUserDate(String userDate) {
        System.out.println("first userDate: " + userDate);
        switch (userDate) {
            case "2018/7/32":
                userDate = "2018/08/01";
                break;
            case "2018/8/32":
                userDate = "2018/09/01";
                break;
            case "2018/9/31":
                userDate = "2018/10/01";
                break;
            case "2018/10/32":
                userDate = "2018/11/01";
                break;
            case "2018/11/31":
                userDate = "2018/12/01";
                break;
            case "2018/12/32":
                userDate = "2019/01/01";
                break;
            case "2019/1/32":
                userDate = "2019/02/01";
                break;
            case "2019/2/29":
                userDate = "2019/03/01";
                break;
            case "2019/3/32":
                userDate = "2019/04/01";
                break;
            case "2019/4/31":
                userDate = "2019/05/01";
                break;
            case "2019/5/32":
                userDate = "2019/06/01";
                break;
            case "2019/6/31":
                userDate = "2019/07/01";
                break;
        }
        System.out.println(userDate);
        
        System.out.println("Got user date here");
        int checkDate = 0; 
        for (String game : gameList) {
            if(game.equals(userDate)) {
                System.out.println("Date already in database");
                return;
            } else {
                checkDate++;
            }
        }
        
        if (checkDate == gameList.size()) {
            gameList.add(userDate);
        }
        
        // EXAMPLE SCHEDULED TASK STRATEGY
        //String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/" + userDate + "/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        String serviceUri = "http://api.sportradar.us/nba/trial/v5/en/games/" + userDate + "/schedule.json?api_key=nbwhs2wr7ktmcagxyh9brpru";
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

            String newDate = userDate.replace("/", "-");
            //String date = game.getScheduled();
            //newDate = date.substring(0, 10);
            newDate = newDate + " 0:00:00.000";
            //System.out.println(newDate);

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

            //Build an instance of the parent object
            // Populate parent object values and call parentRepo.save(parentObj) (Saving over and over again with the same data is fine)
            // The return object from save should be an instance of the object saved - which contains the generated key (if one was created)

            // Build an instance of the child object
            // Populate child object values and call childRepo.save(childObj)
        }
    }
    
    public void setUserDate2(String userMonth, String userYear, String userDay) {
        
        String userDate = userYear + "/" + userMonth + "/" + userDay;
        
/*        int checkDate = 0; 
        for (String game : gameList) {
            if(game.equals(userDate)) {
                System.out.println("Date already in database");
                return;
            } else {
                checkDate++;
            }
        }
        
        if (checkDate == gameList.size()) {
            gameList.add(userDate);
        }*/
        
        System.out.println("Month: " + userMonth);
        System.out.println("Day: "  + userDay);       
        System.out.println("Got user date here");
/*        int checkDate = 0; 
        for (String game : gameListMonth) {
            if(game.equals(userMonth)) {
                System.out.println("Date already in database");
                return;
            } else {
                checkDate++;
            }
        }
        
        if (checkDate == gameListMonth.size()) {
            gameListMonth.add(userMonth);
        }*/
        String day = "";
        String year = "";
        String month = "";
        if(userDay == "01") {
            if(userMonth == "08" || userMonth == "10" || userMonth == "12" || userMonth == "01" || userMonth == "03" || 
                    userMonth == "05" || userMonth == "07") {
                day = "31";
                month = String.valueOf(Integer.valueOf(userMonth) - 1);
            } else if(userMonth == "02") {
                day = "31";
            } else {
                day = "30";
            }
        } else {
            day = String.valueOf(Integer.valueOf(userDay) - 1);
            month = String.valueOf(Integer.valueOf(userMonth));
        }
        
        if (userMonth == "01" && userDay == "01") {
            year = "2018";
        } else {
            year = userYear;
        }

        System.out.println(year + "/" + month + "/" + day);
        
            String apiDate = year + "/" + month + "/" + day;
            System.out.println(apiDate);
            
         // EXAMPLE SCHEDULED TASK STRATEGY
            //String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/" + userDate + "/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
            String serviceUri = "http://api.sportradar.us/nba/trial/v5/en/games/" + apiDate + "/schedule.json?api_key=nbwhs2wr7ktmcagxyh9brpru";
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
                newDate = newDate + " 0:00:00.000";
                //System.out.println(newDate);

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

                //Build an instance of the parent object
                // Populate parent object values and call parentRepo.save(parentObj) (Saving over and over again with the same data is fine)
                // The return object from save should be an instance of the object saved - which contains the generated key (if one was created)

                // Build an instance of the child object
                // Populate child object values and call childRepo.save(childObj)
            }
    }
}
