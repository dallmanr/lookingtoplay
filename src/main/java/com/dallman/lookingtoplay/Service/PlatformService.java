package com.dallman.lookingtoplay.Service;


import com.dallman.lookingtoplay.DTO.IgdbPlatform;
import com.dallman.lookingtoplay.Exception.PlatformNotFoundException;
import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Game.Platform;
import com.dallman.lookingtoplay.Repository.PlatformRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final IgdbPlatformsClient igdbPlatformsClient;

    public PlatformService(PlatformRepository platformRepository,  IgdbPlatformsClient igdbPlatformsClient) {
        this.platformRepository = platformRepository;
        this.igdbPlatformsClient = igdbPlatformsClient;
    }

    @Transactional
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

    @Transactional
    public Platform findOrCreateByPlatformIgdbId(Platform platform) throws EntityNotFoundException {
        Optional<Platform> tempPlatform = platformRepository.findById(platform.getIgdbPlatformId());

        if (tempPlatform.isPresent()) {
            throw new EntityExistsException("Platform with id " + platform.getIgdbPlatformId() + " already exists");
        } else {
            Platform newPlatform = new Platform();
            newPlatform.setIgdbPlatformId(platform.getIgdbPlatformId());
            newPlatform.setName(platform.getName());
            newPlatform.setAbbreviation(platform.getAbbreviation());
            return platformRepository.save(newPlatform);
        }
    }

    public Optional<Platform> findByIgdbPlatformId(Integer igdbPlatformId) {
        return platformRepository.findByIgdbPlatformId(igdbPlatformId);
    }

    public Optional<Platform> findByPlatformName(String platformName) {
        return platformRepository.findByNameContainingIgnoreCase(platformName);
    }


    public void addGameToPlatform(Platform platform, Game game)  {
        // Check the platform exists
        Optional<Platform> verifyPlatform = Optional.of(platformRepository.findByIgdbPlatformId(platform.getIgdbPlatformId())
                .orElseGet(() -> {
                    Platform newPlatform = findOrCreateByPlatformIgdbId(platform);
                    return newPlatform;
                }));

        // Check if the game is already present on the platform
        boolean gameAlreadyOnPlatform = platform.getGames().stream().anyMatch(existingGame -> Objects.equals(existingGame.getId(), game.getId()));

        if (!gameAlreadyOnPlatform) {
            platform.getGames().add(game);
            game.getPlatforms().add(platform);
        }
    }

    @Transactional
    public void save(Platform platform) {
        platformRepository.save(platform);
    }
}
