package de.hackerstolz.berlin.mobility.hacks.eventbrite;

import de.hackerstolz.berlin.mobility.hacks.facebook.Facebook;
import de.hackerstolz.berlin.mobility.hacks.facebook.FacebookStats;
import de.hackerstolz.berlin.mobility.hacks.mobilityhacks.MobilityHacksStats;
import de.hackerstolz.berlin.mobility.hacks.mobilityhacks.MobilityHacksTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

@Service
public class Eventbrite {

    private static final Logger LOG = LoggerFactory.getLogger(Eventbrite.class);

    private static final String EVENTBRITE_SALES_REPORT_URL = "https://www.eventbriteapi.com/v3/reports/sales/?event_ids=27795158066";
    private static final String EVENTBRITE_ATTENDEES_URL = "https://www.eventbriteapi.com/v3/events/27795158066/attendees/?";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Facebook facebook;

    private MobilityHacksStats mobilityHacksStats;

    @Scheduled(fixedRate = 20 * 60 * 1000)
    public void reload() {
        LOG.info("Reloading Stats");
        mobilityHacksStats = loadMobilityHacksStats();
    }

    public MobilityHacksStats getStats() {
        if (mobilityHacksStats == null) {
            mobilityHacksStats = loadMobilityHacksStats();
        }
        return mobilityHacksStats;
    }

    public MobilityHacksStats getNewStats() {
        mobilityHacksStats = null;
        return getStats();
    }

    private MobilityHacksStats loadMobilityHacksStats() {
        EventbriteEventAttendeesResponse report = loadFullDataFromEvenbriteApi();

        MobilityHacksStats result = new MobilityHacksStats();
        result.totalSoldTickets = (long) report.attendees.size();

        for (EventbriteAttendee attendee : report.attendees) {
            incrementTicketCategories(result, attendee);
            if (wasSoldToday(attendee)) {
                result.soldTicketsToday += 1;
            }
            if (wasSoldInTheLastSixtyMinutes(attendee)) {
                LOG.info("wasSoldInTheLastSixtyMinutes!!!");
                result.soldTicketsLastHour += 1;
            }
        }

        FacebookStats facebookStats = facebook.loadFacebookStats();
        result.facebookNumberGoing = facebookStats.going;
        result.facebookNumberInterested = facebookStats.interested;

        return result;
    }

    private boolean wasSoldToday(EventbriteAttendee attendee) {
        return attendee.getCreatedDate().toInstant().isAfter(Instant.now().truncatedTo(ChronoUnit.DAYS));
    }

    private boolean wasSoldInTheLastSixtyMinutes(EventbriteAttendee attendee) {
        Date date = attendee.getCreatedDate();
        Date now = new Date();
        long differenceInMillis = now.getTime() - date.getTime();
        long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
        return differenceInHours <= 1;
    }

    private EventbriteEventAttendeesResponse loadFullDataFromEvenbriteApi() {
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
        return report;
    }

    private void incrementTicketCategories(MobilityHacksStats result, EventbriteAttendee attendee) {
        MobilityHacksTicket ticket = new MobilityHacksTicket();
        ticket.soldTime = attendee.getCreatedDate();
        if (attendee.ticket_class_name.toLowerCase().contains("developer")) {
            result.totalSoldTicketsDeveloper += 1;
            ticket.category = "developer";
        }
        if (attendee.ticket_class_name.toLowerCase().contains("designer")) {
            result.totalSoldTicketsDesigner += 1;
            ticket.category = "designer";
        }
        if (attendee.ticket_class_name.toLowerCase().contains("astronaut")) {
            result.totalSoldTicketsAstronaut += 1;
            ticket.category = "astronaut";
        }
        result.tickets.add(ticket);
    }


}
