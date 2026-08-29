package com.dallman.lookingforgame.Service;

import com.dallman.lookingforgame.DTO.IgdbResponse;
import com.dallman.lookingforgame.Exception.GameNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class IgdbApiService {

    private final WebClient webClient;
    private final IgdbApiProperties igdbApiProperties;
    private final IgdbOAuthClient igdbOAuthClient;

    public IgdbApiService(WebClient webClient, IgdbApiProperties igdbApiProperties, IgdbOAuthClient igdpOAuthClient) {
        this.webClient = webClient;
        this.igdbApiProperties = igdbApiProperties;
        this.igdbOAuthClient = igdpOAuthClient;
    }

    public List<IgdbResponse> searchGameName(String gameName) {
        String token = igdbOAuthClient.getAccessToken();

        String bodyParms = String.format("search \"%s\"; fields name, summary, platforms, cover, first_release_date;",
                gameName.replace("\"", "\\\""));

        List<IgdbResponse> games = webClient.post()
                .uri(igdbApiProperties.getBaseUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Client-ID", igdbApiProperties.getClientId())
                .header("Accept", "application/json")
                .bodyValue(bodyParms)
                .retrieve()
                .onStatus(
                        (HttpStatusCode status) -> status.isError(),
                        (ClientResponse response) -> Mono.error(new RuntimeException(
                                "IGDB API error: " + response.statusCode()))
                )
                .bodyToMono(new ParameterizedTypeReference<List<IgdbResponse>>() {})
                .block();

        if (games == null || games.isEmpty()) {
            throw new GameNotFoundException("No games found matching: " + gameName);
        }

        return games;
    }

    private String formatReleaseDate(Long unixTimestamp) {
        if (unixTimestamp == null || unixTimestamp == 0) {
            return "Unknown";
        }
        return Instant.ofEpochSecond(unixTimestamp).atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

//    private String extractPlatformNames(IgdbPlatform[] platforms) {
//        if (platforms == null || platforms.length == 0) {
//            return null;
//        }
//        return platforms.stream()
//                .map(IgdbPlatform::name)
//                .filter(n -> n != null)
//                .collect(Collectors.joining(", "));
//    }
}
