package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Game.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    // IgdbId is our identifier to check if we have already added this game previously, and it exists in our db
    Optional<Platform> findByIgdbPlatformId(Integer id);

}
