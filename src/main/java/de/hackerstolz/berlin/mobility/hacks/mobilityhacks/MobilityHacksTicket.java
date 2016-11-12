package de.hackerstolz.berlin.mobility.hacks.mobilityhacks;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MobilityHacksTicket {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "Europe/Berlin")
    @JsonProperty("lastUpdateTime")
    public Date soldTime;

    @JsonProperty("category")
    public String category;

}
