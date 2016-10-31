package de.hackerstolz.berlin.mobility.hacks.slack;

import de.hackerstolz.berlin.mobility.hacks.MobilityHacksStats;
import de.hackerstolz.berlin.mobility.hacks.eventbrite.Eventbrite;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This is a Slack Webhook sample. Webhooks are nothing but POST calls to
 * Slack with data relevant to your users. You can send the data
 * in the POST call in either ways:
 * 1) Send as a JSON string as the payload parameter in a POST request
 * 2) Send as a JSON string as the body of a POST request
 */
@Component
public class SlackWebhooks {

    private static final Logger logger = LoggerFactory.getLogger(SlackWebhooks.class);

    /**
     * The Url you get while configuring a new incoming webhook
     * on Slack. You can setup a new incoming webhook
     * <a href="https://my.slack.com/services/new/incoming-webhook/">here</a>.
     */
    @Value("${slackIncomingWebhookUrl}")
    private String slackIncomingWebhookUrl;

    @Autowired
    private Eventbrite eventbrite;


    @Scheduled(fixedRate = 59 * 60 * 1000)
    public void publishStats() {
        MobilityHacksStats stats = eventbrite.getStats();
        if (stats.soldTicketsLastHour > 0) {
            RestTemplate restTemplate = new RestTemplate();
            RichMessage richMessage = new RichMessage("Yeah we sold TICKETS!!! This hour we sold " + stats.soldTicketsLastHour + " ticket(s).");
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
        }
    }

//
//    /**
//     * Make a POST call to the incoming webhook url.
//     */
//    @PostConstruct
//    public void invokeSlackWebhook() {
//        RestTemplate restTemplate = new RestTemplate();
//        RichMessage richMessage = new RichMessage("Just to test Slack's incoming webhooks.");
//        // set attachments
//        Attachment[] attachments = new Attachment[1];
//        attachments[0] = new Attachment();
//        attachments[0].setText("Some data relevant to your users.");
//        richMessage.setAttachments(attachments);
//
//
//        // Always remember to send the encoded message to Slack
//        try {
//            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
//        } catch (RestClientException e) {
//            logger.error("Error posting to Slack Incoming Webhook: ", e);
//        }
//    }
}