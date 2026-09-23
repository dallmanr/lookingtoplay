package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbResponse;
import com.dallman.lookingtoplay.Exception.GameNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class IgdbClient {

    private final WebClient webClient;
    private final IgdbApiProperties igdbApiProperties;
    private final IgdbOAuthClient igdbOAuthClient;

    public IgdbClient(WebClient webClient, IgdbApiProperties igdbApiProperties, IgdbOAuthClient igdpOAuthClient) {
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
                .bodyToMono(new ParameterizedTypeReference<List<IgdbResponse>>() {
                })
                .block();

        if (games == null || games.isEmpty()) {
            throw new GameNotFoundException("No games found matching: " + gameName);
        }

        return games;
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
    public IgdbResponse findById(int id) {
        String token = igdbOAuthClient.getAccessToken();

        String bodyParms = String.format("fields name, summary, platforms, cover, first_release_date; where id = %d;", id);

        List<IgdbResponse> igdbResponse = webClient.post()
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
                .bodyToMono(new ParameterizedTypeReference<List<IgdbResponse>>() {
                })
                .block();
        if (igdbResponse == null ||  igdbResponse.isEmpty()) {
            throw new GameNotFoundException("No games found matching: " + id);
        }

        return igdbResponse.getFirst();
    }

    private String formatReleaseDate(Long unixTimestamp) {
        if (unixTimestamp == null || unixTimestamp == 0) {
            return "Unknown";
        }
        return Instant.ofEpochSecond(unixTimestamp).atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
