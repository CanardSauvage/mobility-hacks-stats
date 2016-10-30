package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventBriteSalesReportResponse {

    public EventBriteSalesReportTotal totals;
}
