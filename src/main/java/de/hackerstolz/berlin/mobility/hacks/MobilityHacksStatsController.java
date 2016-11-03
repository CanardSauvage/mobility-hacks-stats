package de.hackerstolz.berlin.mobility.hacks;

import de.hackerstolz.berlin.mobility.hacks.eventbrite.Eventbrite;
import de.hackerstolz.berlin.mobility.hacks.mobilityhacks.MobilityHacksStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(

        path = "api",
        consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MobilityHacksStatsController {

    @Autowired
    private Eventbrite eventbrite;

    @RequestMapping(
            value = "/stats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public MobilityHacksStats getStats() {
        return eventbrite.getStats();
    }


}
