package com.dallman.lookingforgame.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class IgdbOAuthClient {

    private final IgdbApiProperties props;
    private final WebClient webClient;

    private volatile String accessToken;
    private volatile LocalDateTime tokenExpireTime;

    public IgdbOAuthClient(IgdbApiProperties props, WebClient webClient) {
        this.props = props;
        this.webClient = webClient;
    }

    public String getAccessToken() {
        if (accessToken == null || LocalDateTime.now().isAfter(tokenExpireTime)) {
            refreshAccessToken();
        }

        return accessToken;
    }

    private void refreshAccessToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", props.getClientId());
        formData.add("client_secret", props.getClientSecret());
        formData.add("grant_type", "client_credentials");

        TokenResponse response = webClient.post()
                .uri(props.getTokenEndPoint())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .onStatus(
                        (HttpStatusCode status) -> status.isError(),  // Explicit type
                        (ClientResponse clientResponse) -> Mono.error(new RuntimeException("Twitch auth failed: " + clientResponse.statusCode()))
                )
                .bodyToMono(TokenResponse.class)
                .block();

        if (response == null || response.accessToken() == null) {
            throw new RuntimeException("Invalid token response");
        }

        this.accessToken = response.accessToken();
        this.tokenExpireTime = LocalDateTime.now().plusSeconds(response.expiresIn());
    }

    public record TokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("token_type") String tokenType
    ) {}
}
