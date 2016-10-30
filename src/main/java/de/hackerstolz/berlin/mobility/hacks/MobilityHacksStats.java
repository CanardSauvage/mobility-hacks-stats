package de.hackerstolz.berlin.mobility.hacks;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobilityHacksStats {

    @JsonProperty("totalSoldTickets")
    public Long totalSoldTickets = 0L;

    @JsonProperty("totalSoldTicketsDeveloper")
    public Long totalSoldTicketsDeveloper = 0L;

    @JsonProperty("maxTicketsDeveloper")
    public Long maxTicketsDeveloper = 70L;

    @JsonProperty("totalSoldTicketsDesigner")
    public Long totalSoldTicketsDesigner = 0L;

    @JsonProperty("maxTicketsDesigner")
    public Long maxTicketsDesigner = 20L;

    @JsonProperty("totalSoldTicketsAstronaut")
    public Long totalSoldTicketsAstronaut = 0L;

    @JsonProperty("maxTicketsAstronaut")
    public Long maxTicketsAstronaut = 10L;

    @JsonProperty("daysUntilHackathon")
    public Long daysUntilHackathon = (1480633200000L - System.currentTimeMillis()) / 1000 / 60 / 60 / 24;

//    public static void main(String[] args) throws Exception {
//        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-02");
//        // FIXME Remove System.out
//        System.out.println(parse.getTime());
//// FIXME Remove System.out
//        System.out.println("Current: " + System.currentTimeMillis());
//        // FIXME Remove System.out
//        System.out.println(1480633200000L - System.currentTimeMillis());
//        // FIXME Remove System.out
//        System.out.println((1480633200000L - System.currentTimeMillis()) / 1000 / 60 / 60 / 24);
//    }
}
