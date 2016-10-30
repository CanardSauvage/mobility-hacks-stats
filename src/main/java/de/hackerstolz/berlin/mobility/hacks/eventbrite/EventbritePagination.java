package de.hackerstolz.berlin.mobility.hacks.eventbrite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventbritePagination {

    @JsonProperty("object_count")
    public Long object_count;

    @JsonProperty("page_number")
    public Long page_number;

    @JsonProperty("page_size")
    public Long page_size;

    @JsonProperty("page_count")
    public Long page_count;
}
