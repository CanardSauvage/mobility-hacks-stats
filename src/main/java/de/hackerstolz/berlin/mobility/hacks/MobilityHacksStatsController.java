package de.hackerstolz.berlin.mobility.hacks;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MobilityHacksStatsController {

    private static final String CLIENT_SECRET = "EXB3NWYCJVNR6D7MYRNIUXUTL6L6LCKRTY23GCNYXM7PYG54FV";
    private static final String PERSONAL_OAUTH_TOKEN = "5GKKIH5SCP3KQHQEVZGK";
    private static final String ANONYMOUS_ACCESS_OAUTH_TOKEN = "SEZZGLDJJHBDEBTRDBEN";


    //https://www.eventbriteapi.com/v3/users/me/?token=5GKKIH5SCP3KQHQEVZGK
    //https://www.eventbriteapi.com/v3/reports/sales/?token=5GKKIH5SCP3KQHQEVZGK&event_ids=27795158066


    @RequestMapping(value = "/api/stats", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MobilityHacksStats getStats() {

    }
}
