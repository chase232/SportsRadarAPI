package com.oreillyauto.finalproject.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreillyauto.finalproject.domain.Schedule;
import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.dto.Date;
import com.oreillyauto.finalproject.dto.Text;
import com.oreillyauto.finalproject.service.ScheduledTask;
import com.oreillyauto.finalproject.service.WidgetService;

@Controller
@RequestMapping("/")
@EnableScheduling 
public class WidgetController extends BaseController {
    
    @Autowired
    ScheduledTask scheduledTask;   // Kicks off your custom service
    
    @Autowired
    WidgetService widgetService;


    @GetMapping(value = {"finalproject"})
    public String getWidget(Model model) throws Exception {        
        return "widget";
    }
    
    @ResponseBody
    @GetMapping(value = "finalproject/getGames")
    public List<Widget> getParentGames(){
        
        System.out.println("tried table");
        scheduledTask.setInitialDate();
         
        //List<Widget> gameParentList = widgetService.getAllParentGames();
        
        List<Widget> widgetList = widgetService.getGameByDate("2018-12-16 0:00:00.000");
        return widgetList;
        
        //return gameParentList;
    }
    
    @ResponseBody
    @PostMapping(value = "finalproject/postDate")
    public List<Widget> postDate(Model model, Date date) {
        
        System.out.println(date.getDateOne() + " " + date.getDateTwo());
        scheduledTask.setUserDate(date.getDateOne());
        
        //TimeUnit.SECONDS.sleep(2);
        //scheduledTask.setUserDate2(date.getMonth(), date.getYear(), date.getDay());
        
        String newDate = date.getDateTwo() + " 0:00:00.000";
        List<Widget> widgetList = widgetService.getGameByDate(newDate);
        System.out.println("Made it past list");
        
        for (Widget widget : widgetList) {
            System.out.println("1. " + widget.getDateTime() + widget.getGame());
        }
        return widgetList;
    }
    
/*    @ResponseBody
    @PostMapping(value = "finalproject/getGamesByDate")
    public List<Widget>  getDate(Model model, Date date){
        //System.out.println(date.getDate());
        String newDate = date.getDate() + " 0:00:00.000";
        List<Widget> widgetList = widgetService.getGameByDate(newDate);
        System.out.println("Made it past list");
        for (Widget widget : widgetList) {
            System.out.println("1. " + widget.getDateTime() + widget.getGame());
        }
        return widgetList;
    }*/
    
    @ResponseBody
    @PostMapping(value = { "finalproject/text" })
    public String postSendText(Model model, Text text) throws Exception {
        
        System.out.println("Number: " + text.getPhoneNumber() + " Text: " + text.getTextInformation());
        String body = text.getTextInformation();
        
        if(text.getPhoneNumber() != null && text.getTextInformation() != null) {
            if (body.length() > 160) {
                text.setTextInformation(text.getTextInformation().substring(0, 122));
            }
        }
        try {
            String test = "";
            text.setError(false);
            text.setErrorMessage("");
            test = widgetService.sendText(text);
            if(test.equals("success")) {
                System.out.println("text size: " + text.getTextInformation().length());
                text.setError(false);
                return new ObjectMapper().writeValueAsString(text);
            }else {
                text.setError(true);
                text.setErrorMessage("Phone number needs to be a mobile number and valid\n (+15736945653)");
                return new ObjectMapper().writeValueAsString(text);
            }
        }
        catch (Exception e) {
            try {   
                if(text.getPhoneNumber() == null) {
                    text.setError(true);
                    text.setErrorMessage("Please enter a phone number");
                    return new ObjectMapper().writeValueAsString(text);
                } else if(text.getTextInformation() == null) {
                    text.setError(true);
                    text.setErrorMessage("Please select the check boxes you wish to send");
                    return new ObjectMapper().writeValueAsString(text);
                } else {
                    text.setError(true);
                    text.setErrorMessage(e.getMessage());
                    return new ObjectMapper().writeValueAsString(text);
                } 
            }
            catch (JsonProcessingException jpe) {
                text.setErrorMessage("An error occured " + jpe.getMessage());
                return new JSONObject().put("error", true).put("errorMessage", jpe.getMessage()).toString();
            }
        }
    }
    
/*    @GetMapping(value = {"finalproject/api"})
    public String getAPI(Model model) throws JsonProcessingException {
        String service = "Sports Radar";
        String serviceUri = "http://api.sportradar.us/ncaamb/trial/v4/en/games/2018/12/13/schedule.json?api_key=n53y89q2b7xysgej6ywu9h4m";
        RestTemplate restTemplate = new RestTemplate();
        
        Schedule schedule = restTemplate.getForObject(serviceUri, Schedule.class);
        
        ObjectMapper mapper = new ObjectMapper();
        
        String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedule);
        model.addAttribute("service", service);
        model.addAttribute("request", serviceUri);
        model.addAttribute("response", response);
        
        return "widget";
    }
    
    */
}







