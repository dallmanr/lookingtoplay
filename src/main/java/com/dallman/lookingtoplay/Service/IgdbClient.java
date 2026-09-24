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

        String bodyParms = String.format("search \"%s\"; fields name, summary, platforms, cover, first_release_date;",
                gameName.replace("\"", "\\\""));

        List<IgdbResponse> igdbResponses = callIgdbGamesApi(bodyParms);
        return igdbResponses;
    }

    public IgdbResponse findById(int id) {

        String bodyParms = String.format("fields name, summary, platforms, cover, first_release_date; where id = %d;", id);

        List<IgdbResponse> response = callIgdbGamesApi(bodyParms);
        return response.isEmpty() ? null : response.getFirst();
    }

    public List<IgdbResponse> callIgdbGamesApi(String bodyParams) {
        String token = igdbOAuthClient.getAccessToken();

        List<IgdbResponse> igdbResponse = webClient.post()
                .uri(igdbApiProperties.getBaseUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Client-ID", igdbApiProperties.getClientId())
                .header("Accept", "application/json")
                .bodyValue(bodyParams)
                .retrieve()
                .onStatus(
                        (HttpStatusCode status) -> status.isError(),
                        (ClientResponse response) -> Mono.error(new RuntimeException(
                                "IGDB API error: " + response.statusCode()))
                )
                .bodyToMono(new ParameterizedTypeReference<List<IgdbResponse>>() {
                })
                .block();

        if (igdbResponse == null || igdbResponse.isEmpty()) {
            throw new GameNotFoundException("No matching game(s) found.");
        }

        return igdbResponse;
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
