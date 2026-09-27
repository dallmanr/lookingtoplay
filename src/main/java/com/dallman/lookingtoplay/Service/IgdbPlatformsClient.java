package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbPlatform;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IgdbPlatformsClient {

    private final IgdbApiClient igdbApiClient;

    public IgdbPlatformsClient(IgdbApiClient igdbApiClient) {
        this.igdbApiClient = igdbApiClient;
    }

    public List<IgdbPlatform> getPlatformInfo(List<Integer> igdbPlatforms) {
        StringBuilder platforms = new StringBuilder();
        if (platforms.length() == 1) {
            platforms.append(igdbPlatforms.get(0).toString());
        } else {
           for(int i = 0; i < igdbPlatforms.size(); i++) {
                platforms.append(igdbPlatforms.get(i).toString());
                if (i != igdbPlatforms.size() - 1) {
                    platforms.append(", ");
                }
            }
        }

        String bodyParams = String.format("fields abbreviation, name; where id = (%s);", platforms);

        return igdbApiClient.post("/platforms", bodyParams, new ParameterizedTypeReference<List<IgdbPlatform>>(){});
    }
}
