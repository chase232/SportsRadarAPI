package com.oreillyauto.finalproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Widget implements Serializable {

    private static final long serialVersionUID = 8973255663033274010L;

    @Id
    @Column(name = "event_id", columnDefinition = "BIGINT")
    private String eventID;
    
    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private String dateTime;
    
    @Column(name = "event_type", columnDefinition = "VARCHAR(50)")
    private String eventType;
    
    @Column(name = "sms_sent", columnDefinition = "VARCHAR(1)")
    private String smsSent;
    
    public Widget() {}

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
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
}











