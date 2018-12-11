package com.oreillyauto.finalproject.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.oreillyauto.finalproject.domain.Schedule;

@Component
public class ScheduledTask {
    private final Instant now = Instant.now();
    private final String startTimeString = now.toString();

/*    @Autowired
    ParentRepository parentRepo;
    @Autowired
    ChildRepository childRepo;*/

    @Scheduled(fixedRate = 30000)    //   <=== This method will run every 30 seconds
    public void gatherFooData() {   
        System.out.println("Got here");
        
     // EXAMPLE SCHEDULED TASK STRATEGY
        String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/2018/12/11/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        RestTemplate restTemplate = new RestTemplate();
        
        // Call the service and populate the Response Object
        Schedule responseJSON = restTemplate.getForObject(serviceUri, Schedule.class); 
        Schedule[] schedule = responseJSON.getSchedule(); // Where “Features” is a custom array of data 
 
        //for (Features features : features) {
            // Build an instance of the parent object
            // Populate parent object values and call parentRepo.save(parentObj) (Saving over and over again with the same data is fine)
            // The return object from save should be an instance of the object saved - which contains the generated key (if one was created)

            // Build an instance of the child object
            // Populate child object values and call childRepo.save(childObj)
        //}
    }
}

