package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbResponse;
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

    public List<IgdbResponse> searchGameName(String gameName) {

        String bodyParms = String.format("search \"%s\"; fields name, summary, platforms, cover, first_release_date;",
                gameName.replace("\"", "\\\""));

        return igdbApiClient.post("/games", bodyParms, new ParameterizedTypeReference<List<IgdbResponse>>() {});
    }

    public IgdbResponse findById(int id) {

        String bodyParms = String.format("fields name, summary, platforms, cover, first_release_date; where id = %d;", id);

        List<IgdbResponse> response = igdbApiClient.post("/games", bodyParms,
                new ParameterizedTypeReference<List<IgdbResponse>>() {});

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
