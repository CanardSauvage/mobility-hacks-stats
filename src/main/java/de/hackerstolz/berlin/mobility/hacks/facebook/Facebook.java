package de.hackerstolz.berlin.mobility.hacks.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Facebook {
    private static final Logger LOG = LoggerFactory.getLogger(Facebook.class);

    private static final int ONE_HOUR_IN_MS = 60 * 60_000;

    private static final int FIVE_SECONES_IN_MS = 5_000;

    private static final String MOBILITY_HACKS_EVENT_ID = "842521752516067";

    private FacebookClient.AccessToken accessToken;

    private FacebookClient facebookClient;

    @Value("${facebookAppId}")
    private String facebookAppId;

    @Value("${facebookAppSecret}")
    private String facebookAppSecret;

    public FacebookStats loadFacebookStats() {
        try {
            FacebookClient facebookClient = getFacebookClient();

            Event event = facebookClient.fetchObject(MOBILITY_HACKS_EVENT_ID, Event.class, Parameter.with("fields", "attending_count,interested_count"));

            FacebookStats facebookStats = new FacebookStats();
            facebookStats.interested = event.getInterestedCount();
            facebookStats.going = (long) event.getAttendingCount();
            return facebookStats;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return FacebookStats.EMPTY;
        }
    }

    @Scheduled(fixedDelay = ONE_HOUR_IN_MS, initialDelay = FIVE_SECONES_IN_MS)
    public void loadAccessTokenScheduled() throws Exception {
        loadAccessToken();
    }

    private FacebookClient getFacebookClient() {
        if (facebookClient == null) {
            if (accessToken == null) {
                loadAccessToken();
            }
            facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_7);
        }
        return facebookClient;
    }

    private void loadAccessToken() {
        accessToken = new DefaultFacebookClient(Version.VERSION_2_7).obtainAppAccessToken(facebookAppId, facebookAppSecret);
    }
}
