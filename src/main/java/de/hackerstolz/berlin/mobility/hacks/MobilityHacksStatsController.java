package de.hackerstolz.berlin.mobility.hacks;

import de.hackerstolz.berlin.mobility.hacks.eventbrite.EventBriteSalesReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(

        path = "api",
        consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MobilityHacksStatsController {

    private static final String EVENTBRITE_SALES_REPORT_URL = "https://www.eventbriteapi.com/v3/reports/sales/?event_ids=27795158066";

    @Autowired
    private RestTemplate restTemplate;

    private MobilityHacksStats mobilityHacksStats;

    @RequestMapping(
            value = "/stats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public MobilityHacksStats getStats() {
        EventBriteSalesReportResponse report = restTemplate.getForObject(EVENTBRITE_SALES_REPORT_URL, EventBriteSalesReportResponse.class);

        MobilityHacksStats result = mobilityHacksStats;
        if (result == null) {
            result = new MobilityHacksStats();
            result.totalSoldTickets = report.totals.quantity;
            mobilityHacksStats = result;
        }

        return result;
    }
}
