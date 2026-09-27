package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Game.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    Optional<Platform> findByIgdbPlatformId(Integer id);

}
