package de.hackerstolz.berlin.mobility.hacks.slack;

import de.hackerstolz.berlin.mobility.hacks.mobilityhacks.MobilityHacksStats;
import de.hackerstolz.berlin.mobility.hacks.eventbrite.Eventbrite;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.regex.Matcher;

@Component
public class SlackBot extends Bot {

    private static final Logger LOG = LoggerFactory.getLogger(SlackBot.class);

    @Autowired
    private Eventbrite eventbrite;

    /**
     * Slack token from application.properties file. You can get your slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    /**
     * Invoked when the bot receives a direct mention (@botname: message)
     * or a direct message. NOTE: These two event types are added by jbot
     * to make your task easier, Slack doesn't have any direct way to
     * determine these type of events.
     *
     * @param session
     * @param event
     */
    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDirectMessage(WebSocketSession session, Event event) {
        try {
            LOG.info("Received DirectMessage: " + event.getText() + " from " + getUserName(event));
            MobilityHacksStats stats = eventbrite.getStats();
            reply(session, event, new Message("We sold " + stats.totalSoldTickets + " tickets overall and today we sold " + stats.soldTicketsToday + "! Want to know more? Say 'full stats'."));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying
     * the pattern {@code ([a-z ]{2})(\d+)([a-z ]{2})}. For example,
     * messages like "ab12xy" or "ab2bc" etc will invoke this method.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE, pattern = "(full stats)")
    public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
        try {
            MobilityHacksStats stats = eventbrite.getStats();

            String text = event.getText();
            if (text.startsWith("Our current full stats") || text.startsWith("We sold")) {
                LOG.info("Received message from myself. Skipping.");
                return;
            }

            LOG.info("Received Message " + text + " from " + getUserName(event) + " with matcher '" + matcher.group() + "'");

            String answer = "Our current full stats:\n" +
                    "we sold " + stats.totalSoldTickets + " tickets overall, " +
                    "" + stats.soldTicketsToday + " today, " +
                    "" + stats.soldTicketsLastHour + " in the last hour.\n" +
                    "And by Dev/Designer/Astronaut we sold " +
                    stats.totalSoldTicketsDeveloper + " / " + stats.totalSoldTicketsDesigner + " / " + stats.totalSoldTicketsAstronaut + ".\n" +
                    "We have *" + stats.daysUntilHackathon + "* days until the Hackathon.\n" +
                    "" + stats.facebookNumberInterested + " people are interested in our facebook event and "
                    + stats.facebookNumberGoing + " people say they are going.";
            reply(session, event, new Message(answer));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String getUserName(Event event) {
        if (event.getUser() != null) {
            return " {name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "}";
        }
        return event.getUserId();
    }

    /**
     * Invoked when an item is pinned in the channel.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.PIN_ADDED)
    public void onPinAdded(WebSocketSession session, Event event) {
        reply(session, event, new Message("Thanks for the pin! You can find all pinned items under channel details."));
    }

//    /**
//     * Invoked when bot receives an event of type file shared.
//     * NOTE: You can't reply to this event as slack doesn't send
//     * a channel id for this event type. You can learn more about
//     * <a href="https://api.slack.com/events/file_shared">file_shared</a>
//     * event from Slack's Api documentation.
//     *
//     * @param session
//     * @param event
//     */
//    @Controller(events = EventType.FILE_SHARED)
//    public void onFileShared(WebSocketSession session, Event event) {
//        logger.info("File shared: {}", event);
//    }
//
//
//    /**
//     * Conversation feature of JBot. This method is the starting point of the conversation (as it
//     * calls {@link Bot#startConversation(Event, String)} within it. You can chain methods which will be invoked
//     * one after the other leading to a conversation. You can chain methods with {@link Controller#next()} by
//     * specifying the method name to chain with.
//     *
//     * @param session
//     * @param event
//     */
//    @Controller(pattern = "(setup meeting)", next = "confirmTiming")
//    public void setupMeeting(WebSocketSession session, Event event) {
//        startConversation(event, "confirmTiming");   // start conversation
//        reply(session, event, new Message("Cool! At what time (ex. 15:30) do you want me to set up the meeting?"));
//    }
//
//    /**
//     * This method is chained with {@link SlackBot#setupMeeting(WebSocketSession, Event)}.
//     *
//     * @param session
//     * @param event
//     */
//    @Controller(next = "askTimeForMeeting")
//    public void confirmTiming(WebSocketSession session, Event event) {
//        reply(session, event, new Message("Your meeting is set at " + event.getText() +
//                ". Would you like to repeat it tomorrow?"));
//        nextConversation(event);    // jump to next question in conversation
//    }
//
//    /**
//     * This method is chained with {@link SlackBot#confirmTiming(WebSocketSession, Event)}.
//     *
//     * @param session
//     * @param event
//     */
//    @Controller(next = "askWhetherToRepeat")
//    public void askTimeForMeeting(WebSocketSession session, Event event) {
//        if (event.getText().contains("yes")) {
//            reply(session, event, new Message("Okay. Would you like me to set a reminder for you?"));
//            nextConversation(event);    // jump to next question in conversation
//        } else {
//            reply(session, event, new Message("No problem. You can always schedule one with 'setup meeting' command."));
//            stopConversation(event);    // stop conversation only if user says no
//        }
//    }
//
//    /**
//     * This method is chained with {@link SlackBot#askTimeForMeeting(WebSocketSession, Event)}.
//     *
//     * @param session
//     * @param event
//     */
//    @Controller
//    public void askWhetherToRepeat(WebSocketSession session, Event event) {
//        if (event.getText().contains("yes")) {
//            reply(session, event, new Message("Great! I will remind you tomorrow before the meeting."));
//        } else {
//            reply(session, event, new Message("Oh! my boss is smart enough to remind himself :)"));
//        }
//        stopConversation(event);    // stop conversation
//    }


}