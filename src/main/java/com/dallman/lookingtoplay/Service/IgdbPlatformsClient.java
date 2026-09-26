package com.dallman.lookingtoplay.Service;

import org.springframework.stereotype.Service;

@Service
public class IgdbPlatformsClient {

    private final IgdbApiClient igdbApiClient;

    public IgdbPlatformsClient(IgdbApiClient igdbApiClient) {
        this.igdbApiClient = igdbApiClient;
    }
}
