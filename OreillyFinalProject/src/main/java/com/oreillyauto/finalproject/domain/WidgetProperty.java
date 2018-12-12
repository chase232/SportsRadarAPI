package com.oreillyauto.finalproject.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event_properties")
public class WidgetProperty implements Serializable {

    private static final long serialVersionUID = 8973255663033274010L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", columnDefinition = "BIGINT")
    private BigInteger propertyID;
    
    @Column(name = "event_key", columnDefinition = "VARCHAR(100)")
    private String eventKey;
    
    @Column(name = "event_value", columnDefinition = "VARCHAR(160)")
    private String eventValue;
    
    @JsonIgnore
    @ManyToOne
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition="BIGINT")
    private Widget widget;
    
    public WidgetProperty(){}

    public BigInteger getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(BigInteger propertyID) {
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











