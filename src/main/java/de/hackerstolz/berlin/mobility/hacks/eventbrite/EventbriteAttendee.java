package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EventbriteAttendee {

    // eg. ": "2016-10-01T08:54:30Z",
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ssZ", timezone = "Europe/Berlin")
    public Date created;

    @JsonProperty("ticket_class_name")
    public String ticket_class_name;

}
