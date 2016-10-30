package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventBriteSalesReportTotal {

    public String currency;
    public String gross;
    public String net;
    public Long quantity;
    public String fees;
}
