package com.dallman.lookingtoplay.Service;

import org.springframework.stereotype.Component;

@Component
public class IgdbPlatformsClient {

    private final IgdbApiClient igdbApiClient;

    public IgdbPlatformsClient(IgdbApiClient igdbApiClient) {
        this.igdbApiClient = igdbApiClient;
    }
}
