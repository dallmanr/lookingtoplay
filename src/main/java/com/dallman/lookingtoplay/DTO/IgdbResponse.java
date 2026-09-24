package com.dallman.lookingtoplay.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

public record IgdbResponse(
        int id,
        String name,
        String summary,
        //The API returns the field name as first_release_date so we can map it to firstReleaseDate
        // with the use of @JsonProperty
        @JsonProperty("first_release_date")
        Long firstReleaseDate,
        @JsonProperty("total_rating")
        Double totalRating,
        Integer cover,
        @JsonProperty("game_status")
        String gameStatus,
        List<Integer> platforms
) { }

