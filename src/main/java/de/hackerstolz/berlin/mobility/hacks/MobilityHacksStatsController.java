package de.hackerstolz.berlin.mobility.hacks;

import de.hackerstolz.berlin.mobility.hacks.eventbrite.EventbriteAttendee;
import de.hackerstolz.berlin.mobility.hacks.eventbrite.EventbriteEventAttendeesResponse;
import de.hackerstolz.berlin.mobility.hacks.slack.SlackBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping(

        path = "api",
        consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MobilityHacksStatsController {

    private static final String EVENTBRITE_SALES_REPORT_URL = "https://www.eventbriteapi.com/v3/reports/sales/?event_ids=27795158066";
    private static final String EVENTBRITE_ATTENDEES_URL = "https://www.eventbriteapi.com/v3/events/27795158066/attendees/?";
    private static final long FORTY_MINUTES_IN_MS = 40 * 60 * 1000;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SlackBot slackBot;

    private MobilityHacksStats mobilityHacksStats;

    @Scheduled(fixedRate = FORTY_MINUTES_IN_MS)
    public void work() {
        mobilityHacksStats = null;
        slackBot.publishStats();
    }

    @RequestMapping(
            value = "/stats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public MobilityHacksStats getStats() {
        // https://www.eventbriteapi.com/v3/reports/attendees/?event_ids=27795158066
        MobilityHacksStats result = mobilityHacksStats;
        if (result == null) {
            result = getMobilityHacksStats();
            mobilityHacksStats = result;
        }


        return result;
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
