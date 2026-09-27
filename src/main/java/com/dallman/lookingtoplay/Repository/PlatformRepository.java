package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Game.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {
}
