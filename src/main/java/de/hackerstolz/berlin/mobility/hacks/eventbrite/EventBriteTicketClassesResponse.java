package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventBriteTicketClassesResponse {

    @JsonProperty("ticket_classes")
    public List<EventBriteTicketClasses> ticketClasses;
}
