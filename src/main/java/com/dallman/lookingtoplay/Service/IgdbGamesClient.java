package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class IgdbGamesClient {

    private final IgdbApiClient igdbApiClient;

    public IgdbGamesClient(IgdbApiClient igdbApiClient) {
        this.igdbApiClient = igdbApiClient;
    }

    public List<IgdbGame> searchGameName(String gameName) {

        String bodyParms = String.format("search \"%s\"; fields name, summary, platforms, cover, first_release_date, status, total_rating, url, game_type;",
                gameName.replace("\"", "\\\""));

        return igdbApiClient.post("/games", bodyParms, new ParameterizedTypeReference<List<IgdbGame>>() {});
    }

    public IgdbGame findById(int id) {

        String bodyParms = String.format("fields name, summary, platforms, cover, first_release_date, status, total_rating, url, game_type; " +
                "where id = %d;", id);

        List<IgdbGame> response = igdbApiClient.post("/games", bodyParms,
                new ParameterizedTypeReference<List<IgdbGame>>() {});

        return response.isEmpty() ? null : response.getFirst();
    }

    private String formatReleaseDate(Long unixTimestamp) {
        if (unixTimestamp == null || unixTimestamp == 0) {
            return "Unknown";
        }
        return Instant.ofEpochSecond(unixTimestamp).atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
