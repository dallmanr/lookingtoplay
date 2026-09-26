package com.dallman.lookingtoplay.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class IgdbApiClient {

    private final WebClient webClient;
    private final IgdbApiProperties igdbApiProperties;
    private final IgdbOAuthClient igdbOAuthClient;

    public IgdbApiClient(WebClient webClient, IgdbApiProperties igdbApiProperties, IgdbOAuthClient igdbOAuthClient) {
        this.webClient = webClient;
        this.igdbApiProperties = igdbApiProperties;
        this.igdbOAuthClient = igdbOAuthClient;
    }

    public <T> List<T> post(String endPoint, String bodyParams, ParameterizedTypeReference<List<T>> responseType) {

        String token = igdbOAuthClient.getAccessToken();

        return webClient.post()
                .uri(igdbApiProperties.getBaseUrl() + endPoint)
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
                .bodyToMono(responseType)
                .block();
    }
}
