package de.hackerstolz.berlin.mobility.hacks.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Facebook {

    @Value("${facebookToken}")
    private String facebookToken;

    public static class FacebookStats {
        public Long interested;
        public Long going;
    }

    public FacebookStats loadFacebookStats() {
        FacebookClient facebookClient = new DefaultFacebookClient(facebookToken, Version.VERSION_2_8);

        Event event = facebookClient.fetchObject("842521752516067", Event.class, Parameter.with("fields", "attending_count,interested_count"));

        FacebookStats facebookStats = new FacebookStats();
        facebookStats.interested = event.getInterestedCount();
        facebookStats.going = (long) event.getAttendingCount();
        return facebookStats;
    }
}
