package com.dallman.lookingtoplay.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// @JsonProperty lets us match the value returned by the API to our field name

public record IgdbGame(
        int id,
        String name,
        String summary,
        @JsonProperty("first_release_date")
        Long firstReleaseDate,
        @JsonProperty("total_rating")
        Double totalRating,
        Integer cover,
        @JsonProperty("game_status")
        Integer gameStatus,
        String url,
        @JsonProperty("game_type")
        Integer gameType,
        List<Integer> platforms
) { }

