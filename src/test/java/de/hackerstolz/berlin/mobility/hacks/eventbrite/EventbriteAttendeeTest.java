package de.hackerstolz.berlin.mobility.hacks.eventbrite;

import org.junit.Test;

import java.time.Instant;

public class EventbriteAttendeeTest {

    @Test
    public void dateFormating() throws Exception {
        EventbriteAttendee eventbriteAttendee = new EventbriteAttendee();
        eventbriteAttendee.created = "2016-10-01T08:54:30Z";
        Instant createdInstant = eventbriteAttendee.getCreatedInstant();

    }
}