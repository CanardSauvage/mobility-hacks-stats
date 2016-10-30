package de.hackerstolz.berlin.mobility.hacks.eventbrite;

import de.hackerstolz.berlin.mobility.hacks.MobilityHacksStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class Eventbrite {

    private static final String EVENTBRITE_SALES_REPORT_URL = "https://www.eventbriteapi.com/v3/reports/sales/?event_ids=27795158066";
    private static final String EVENTBRITE_ATTENDEES_URL = "https://www.eventbriteapi.com/v3/events/27795158066/attendees/?";
    @Autowired
    private RestTemplate restTemplate;

    private MobilityHacksStats mobilityHacksStats;


    public void reload() {
        mobilityHacksStats = getMobilityHacksStats();

    }

    public MobilityHacksStats getStats() {
        if (mobilityHacksStats == null) {
            mobilityHacksStats = getMobilityHacksStats();
        }
        return mobilityHacksStats;
    }

    private MobilityHacksStats getMobilityHacksStats() {
        EventbriteEventAttendeesResponse report = restTemplate.getForObject(EVENTBRITE_ATTENDEES_URL, EventbriteEventAttendeesResponse.class);
        report.attendees = new ArrayList<>(report.attendees);

        if (report.pagination.page_count > 1) {
            EventbriteEventAttendeesResponse report2 = restTemplate.getForObject(EVENTBRITE_ATTENDEES_URL + "page=1", EventbriteEventAttendeesResponse.class);
            report.attendees.addAll(report2.attendees);
        }
        if (report.pagination.page_count >= 2) {
            EventbriteEventAttendeesResponse report2 = restTemplate.getForObject(EVENTBRITE_ATTENDEES_URL + "page=2", EventbriteEventAttendeesResponse.class);
            report.attendees.addAll(report2.attendees);
        }

        MobilityHacksStats result;
        result = new MobilityHacksStats();
        result.totalSoldTickets = Long.valueOf(report.attendees.size());
        for (EventbriteAttendee ticketClass : report.attendees) {
            // Developer Designer Astronaut
            if (ticketClass.ticket_class_name.toLowerCase().contains("developer")) {
                result.totalSoldTicketsDeveloper += 1;
            }
            if (ticketClass.ticket_class_name.toLowerCase().contains("designer")) {
                result.totalSoldTicketsDesigner += 1;
            }
            if (ticketClass.ticket_class_name.toLowerCase().contains("astronaut")) {
                result.totalSoldTicketsAstronaut += 1;
            }
        }
        return result;
    }
}
