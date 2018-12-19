// Class:       WidgetServiceImpl
// Purpose:     The business logic of the project is here
//                  performs business functions and does a majority of the 
//                  logic 

package com.oreillyauto.finalproject.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreillyauto.finalproject.dao.WidgetChildRepository;
import com.oreillyauto.finalproject.dao.WidgetParentRepository;
import com.oreillyauto.finalproject.domain.Widget;
import com.oreillyauto.finalproject.domain.WidgetProperty;
import com.oreillyauto.finalproject.dto.Text;


@Service("widgetService")
public class WidgetServiceImpl implements WidgetService {

    @Autowired
    WidgetParentRepository widgetParentRepo;
    
    @Autowired
    WidgetChildRepository widgetChildRepo;

    @Override
    public List<Widget> getAllParentGames() {
        return (List<Widget>)widgetParentRepo.findAll();
    }
    
    @Override
    public List<WidgetProperty> getAllChildGames() {
        return (List<WidgetProperty>)widgetChildRepo.findAll();
    }

    @Override
    public String sendText(Text text) {
        
        String phoneNumber = text.getPhoneNumber();
        String textInformation = text.getTextInformation();
      
        TwilioServiceImpl t = null;
        try {
            t = new TwilioServiceImpl();
            String message = t.sendSms(phoneNumber, textInformation);
            
            return message;
        }
        catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }     
    }

    @Override
    public List<Widget> getGameByDate(String newDate) {
        List<Widget> gamesList =  widgetParentRepo.findByDateTime(newDate);
        return gamesList;
    }

    @Override
    public void saveGame(Widget w) {
        widgetParentRepo.save(w);
    }

    @Override
    public Widget findByEventID(int i) {
        BigInteger bigInt = BigInteger.valueOf(i);
        Widget w = widgetParentRepo.findByEventID(bigInt);
        return w;
    }

    @Override
    public List<Widget> findBySmsSent(String yes) {
        List<Widget> w =  widgetParentRepo.findBySmsSent(yes);
        return w;
    }

    @Override
    public String checkTextSize(String textInformation, String phoneNumber, String body) {
        
        if(phoneNumber != null && textInformation != null) {
            if (body.length() > 160) {
                return textInformation.substring(0, 122);
            } else {
                return textInformation;
            }
        } else {
            return null;
        }
    }
}
