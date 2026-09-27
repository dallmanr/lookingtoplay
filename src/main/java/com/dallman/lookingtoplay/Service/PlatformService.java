package com.dallman.lookingtoplay.Service;


import com.dallman.lookingtoplay.DTO.IgdbPlatform;
import com.dallman.lookingtoplay.Game.Platform;
import com.dallman.lookingtoplay.Repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final IgdbPlatformsClient igdbPlatformsClient;

    public PlatformService(PlatformRepository platformRepository,  IgdbPlatformsClient igdbPlatformsClient) {
        this.platformRepository = platformRepository;
        this.igdbPlatformsClient = igdbPlatformsClient;
    }

    public List<Platform> findOrCreateByIgdbIds(List<Integer> igdbIds) {
        if (igdbIds == null || igdbIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<IgdbPlatform> igdbPlatforms =
                igdbPlatformsClient.getPlatformInfo(igdbIds);

        List<Platform> result = new ArrayList<>();

        for (IgdbPlatform igdbPlatform : igdbPlatforms) {
            Platform platform = platformRepository
                    .findByIgdbPlatformId(igdbPlatform.id())
                    .orElseGet(() -> {
                        Platform newPlatform = new Platform();
                        newPlatform.setIgdbPlatformId(igdbPlatform.id());
                        newPlatform.setName(igdbPlatform.name());
                        newPlatform.setAbbreviation(igdbPlatform.abbreviation());

                        return platformRepository.save(newPlatform);
                    });

            result.add(platform);
        }

        return result;
    }
}
