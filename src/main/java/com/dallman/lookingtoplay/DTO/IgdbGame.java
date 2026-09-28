package com.dallman.lookingtoplay.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*
* This is the response we get from the IGDB /games API
* We don't map every field, just current ones of interest to this IgdbRecord
* We will use this record for creating a new instance of a Game if required
* Platforms are returned to us in this API, but only as a list of Ints
* To obtain actual Platform information, we need to call a separate /platforms API
*
* @JsonProperty lets us match the value returned by the API to our field name as they are not 1:1
* */

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

