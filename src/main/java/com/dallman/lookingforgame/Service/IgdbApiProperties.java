package com.dallman.lookingforgame.Service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix="igdb.api")
public class IgdbApiProperties {

    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String authEndPoint;
    private String tokenEndPoint;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthEndPoint() {
        return authEndPoint;
    }

    public void setAuthEndPoint(String authEndPoint) {
        this.authEndPoint = authEndPoint;
    }

    public String getTokenEndPoint() {
        return tokenEndPoint;
    }

    public void setTokenEndPoint(String tokenEndPoint) {
        this.tokenEndPoint = tokenEndPoint;
    }
}
