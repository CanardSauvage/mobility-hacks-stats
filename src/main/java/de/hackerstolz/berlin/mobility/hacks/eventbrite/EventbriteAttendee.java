package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventbriteAttendee {

    // eg. ": "2016-10-01T08:54:30Z",
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ssZ", timezone = "Europe/Berlin")
//    public Date created;

    public String created;

    public Instant createdInstant;

    public Instant getCreatedInstant() {
        if (createdInstant == null) {
            try {
                createdInstant = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(created).toInstant();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return createdInstant;
    }

    @JsonProperty("ticket_class_name")
    public String ticket_class_name;

}
