package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventbriteEventAttendeesResponse {

    public EventbritePagination pagination;

    @JsonProperty("attendees")
    public List<EventbriteAttendee> attendees;
}
