package de.hackerstolz.berlin.mobility.hacks.mobilityhacks;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MobilityHacksStats {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Europe/Berlin")
    @JsonProperty("lastUpdateTime")
    public Date lastUpdateTime = new Date();

    @JsonProperty("totalSoldTickets")
    public Long totalSoldTickets = 0L;

    @JsonProperty("soldTicketsToday")
    public Long soldTicketsToday = 0L;

    @JsonProperty("soldTicketsLastHour")
    public Long soldTicketsLastHour = 0L;

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

    @JsonProperty("facebookNumberInterested")
    public Long facebookNumberInterested = 0L;

    @JsonProperty("facebookNumberGoing")
    public Long facebookNumberGoing = 0L;

    @JsonProperty("tickets")
    public List<MobilityHacksTicket> tickets = new ArrayList<>();
}
