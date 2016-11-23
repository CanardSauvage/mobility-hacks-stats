package de.hackerstolz.berlin.mobility.hacks.eventbrite;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventbriteAttendee {

    // eg. ": "2016-10-01T08:54:30Z",
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ssZ", timezone = "Europe/Berlin")
//    public Date created;

    @JsonProperty("created")
    public String created;

    @JsonProperty("cancelled")
    public boolean cancelled;

    @JsonProperty("refunded")
    public boolean refunded;

    private Date createdDate;

    public Date getCreatedDate() {
        if (createdDate == null) {
            try {
                createdDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(created);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return createdDate;
    }

    @JsonProperty("ticket_class_name")
    public String ticket_class_name;

    @Override
    public String toString() {
        return "EventbriteAttendee{" +
                "created='" + created + '\'' +
                ", createdDate=" + createdDate +
                ", ticket_class_name='" + ticket_class_name + '\'' +
                '}';
    }
}
