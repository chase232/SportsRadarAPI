package com.oreillyauto.finalproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "events_properties")
public class WidgetProperty implements Serializable {

    private static final long serialVersionUID = 8973255663033274010L;

    @Id
    @Column(name = "property_id", columnDefinition = "BIGINT")
    private String propertyID;
    
    @Column(name = "event_key", columnDefinition = "VARCHAR(50)")
    private String eventKey;
    
    @Column(name = "event_value", columnDefinition = "VARCHAR(160)")
    private String eventValue;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition="BIGINT")
    private Widget widget;
    
    WidgetProperty(){}

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
}











