/**
 * Class:       WidgetController
 * Developer:   Chase Dickerson
 * Date:        12/19/2018
 * Purpose:     Controls the flow and direction of the project
 */

package com.oreillyauto.finalproject.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ScheduledTask scheduledTask;  
    
    @Autowired
    WidgetService widgetService;

    // Initial get mapping to access the view 
    @GetMapping(value = {"finalproject"})
    public String getWidget(Model model) throws Exception {        
        return "widget";
    }
    
    // Sets the initial date for the table
    // Could easily set default to today's date 
    //      but 12/16/2018 is used for simplicity and consistency 
    @ResponseBody
    @GetMapping(value = "finalproject/getGames")
    public List<Widget> getParentGames(){
        
        scheduledTask.setInitialDate();
        List<Widget> widgetList = widgetService.getGameByDate("2018-12-16 0:00:00.000");
        
        return widgetList;
    }
    
    // Sets the date desired by the user and returns the information
    //  for the given date 
    @ResponseBody
    @PostMapping(value = "finalproject/postDate")
    public List<Widget> postDate(Model model, Date date) {
        
        scheduledTask.setUserDate(date.getDateOne());       
        String newDate = date.getDateTwo() + " 0:00:00.000";  
        List<Widget> widgetList = widgetService.getGameByDate(newDate);

        return widgetList;
    }
    
    // Directs the flow of the text message being sent and returns
    //  success, failure, or landline
    @ResponseBody
    @PostMapping(value = { "finalproject/text" })
    public String postSendText(Model model, Text text) throws Exception {
        
        String body = text.getTextInformation();
        String textSize = widgetService.checkTextSize(text.getTextInformation(), text.getPhoneNumber(), body);
        if(textSize != null) {
            text.setTextInformation(textSize);
        }
        
        // Moved to service. Kept just in case it doesn't work
/*        if(text.getPhoneNumber() != null && text.getTextInformation() != null) {
            if (body.length() > 160) {
                text.setTextInformation(text.getTextInformation().substring(0, 122));
            }
        }*/
        
        try {
            String test = "";
            text.setError(false);
            text.setErrorMessage("");
            test = widgetService.sendText(text);
            if(test.equals("success")) {
               text.setError(false);                
               int sentArray[] = text.getIds();
               for (int i : sentArray) {
                    Widget w = widgetService.findByEventID(i);
                    w.setSmsSent("Y");
                    widgetService.saveGame(w);
                }
                return new ObjectMapper().writeValueAsString(text);
            }else {
                text.setError(true);
                if (test.equals("landline")) {
                    text.setErrorMessage("Phone number cannot be a landline number");
                } else {
                    text.setErrorMessage("Phone number needs to be a mobile number (no landline) and valid\n (+15736945653)");
                }              
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
    
    // Whenever a text message is sent successfully 
    //   this will save it to the database
    @ResponseBody
    @GetMapping(value = { "finalproject/getSmsSent" })
    public List<BigInteger> getSmsSent(Model model) throws Exception {
        String yes = "Y";
        BigInteger id;
        List<BigInteger> idList = new ArrayList<>();
        List<Widget> sentList = widgetService.findBySmsSent(yes);
        for (Widget widget : sentList) {
            id = widget.getEventID();
            idList.add(id);
        }
        
        return idList;
    }
}







