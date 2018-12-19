// Class:       Widget
// Purpose:     Serves as a connector to the database table events
//                  also contains methods used to populate table in view

package com.oreillyauto.finalproject.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Widget implements Serializable {

    private static final long serialVersionUID = 8973255663033274010L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", columnDefinition = "BIGINT")
    private BigInteger eventID;
    
    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private String dateTime;
    
    @Column(name = "event_type", columnDefinition = "VARCHAR(100)")
    private String eventType;
    
    @Column(name = "sms_sent", columnDefinition = "VARCHAR(1)")
    private String smsSent;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "widget")
    private List<WidgetProperty> widgetPropertiesList = new ArrayList<>();
    
    public Widget() {}
    
    public List<WidgetProperty> getWidgetPropertiesList() {
        return widgetPropertiesList;
    }

    public void setWidgetPropertiesList(List<WidgetProperty> widgetPropertiesList) {
        this.widgetPropertiesList = widgetPropertiesList;
    }

    public BigInteger getEventID() {
        return eventID;
    }

    public void setEventID(BigInteger eventID) {
        this.eventID = eventID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSmsSent() {
        return smsSent;
    }

    public void setSmsSent(String smsSent) {
        this.smsSent = smsSent;
    }
    
    public String getEventLocation() {
        
        String location = " ";
        for (WidgetProperty widgetProperty : widgetPropertiesList) {
            return widgetProperty.getEventKey();
        }
        return location;
    }
    
    public String getGame() {
        
        String game = " ";
        for (WidgetProperty widgetProperty : widgetPropertiesList) {
            return widgetProperty.getEventValue();
        }
        return game;
    }
    
    public String getDateTimeString() {
        String date = getDateTime();
        String newDate = date.substring(0, 10);
        return newDate;
    }
}











