package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonProperty;

public class EventbriteAttendee {

    // eg. ": "2016-10-01T08:54:30Z",
    public String created;

    @JsonProperty("ticket_class_name")
    public String ticket_class_name;

}
