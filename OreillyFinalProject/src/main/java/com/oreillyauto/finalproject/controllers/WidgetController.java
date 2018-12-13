package com.oreillyauto.finalproject.controllers;

import java.util.List;

import org.hibernate.validator.constraints.Email;
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
import com.oreillyauto.finalproject.domain.WidgetProperty;
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
        System.out.println("Got here");
        
        List<Widget> widgetParentList = widgetService.getAllParentGames();
        
        for (Widget widget : widgetParentList) {
            System.out.println(widget.getDateTime());
        }
        
        List<WidgetProperty> widgetChildList = widgetService.getAllChildGames();
        
        for (WidgetProperty wp : widgetChildList) {
            System.out.println(wp.getEventKey());
        }
        
        return "widget";
    }
    
    @ResponseBody
    @GetMapping(value = "finalproject/getGames")
    public List<Widget> getParentGames(){
        
        System.out.println("tried table");
        
        Widget w = new Widget();   
        List<Widget> gameParentList = widgetService.getAllParentGames();
        
        return gameParentList;
    }
    
    @ResponseBody
    @PostMapping(value = { "finalproject/text" })
    public String postContactUs(Model model, Email email) throws Exception {
        return "widget";
    }
    
    @GetMapping(value = {"finalproject/api"})
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
    
    
}







