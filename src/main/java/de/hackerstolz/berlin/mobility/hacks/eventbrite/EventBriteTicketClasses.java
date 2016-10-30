package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventBriteTicketClasses {

   public String name;

    @JsonProperty("quantity_total")
    public String quantityTotal;

    @JsonProperty("quantity_sold")
    public String quantitySold;
}
