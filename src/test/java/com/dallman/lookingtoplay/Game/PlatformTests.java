package com.dallman.lookingtoplay.Game;


import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Repository.PlatformRepository;
import com.dallman.lookingtoplay.Service.GameService;
import com.dallman.lookingtoplay.Service.PlatformService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/.env.test.properties")
public class PlatformTests {

    @Autowired
    PlatformRepository platformRepository;

    @Autowired
    PlatformService platformService;

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;


    /*
    * assertThrows needs lambda expression: ExceptionType.class, () -> method that throws
    * 1. We can't save duplicate igdb platform ids
    * 2. Find by name - ignore case, fuzzy search
    * 3. Find by igdb ID
    * */


    @BeforeEach
    public void beforeEach() {
        Platform platform = new Platform(1, "PC", "Computer");
        platformRepository.save(platform);
        Game gameOne = new Game(1,"Game one", 1,"Game summary", 1L, 1.0, "www.website.com", 1, 1, new ArrayList<>(List.of(platform)));
        Game gameTwo = new Game(2,"Game Two", 2,"Game summary", 2L, 2.0, "www.website.com", 2, 2, new ArrayList<>(List.of(platform)));
        gameRepository.save(gameOne);
        gameRepository.save(gameTwo);
        platformService.addGameToPlatform(platform, gameOne);
        platformService.addGameToPlatform(platform, gameTwo);

        platformRepository.save(platform);
        gameRepository.save(gameOne);
        gameRepository.save(gameTwo);

    }

    @AfterEach
    public void afterEach() {
        platformRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    @DisplayName("Can't add same IGDB id again")
    @Transactional
    public void cannotSaveDuplicateIgdbPlatform() {
        // Check that duplicate throws the expected exception
        assertThrows(EntityExistsException.class, () -> platformService.findOrCreateByPlatformIgdbId(new Platform(1, "PC", "Computer")));
        // Check we can still save a non-duplicate platform
        assertNotNull(platformService.findOrCreateByPlatformIgdbId(new Platform(2, "SW", "Nintendo Switch")));
    }

    @Test
    @DisplayName("Search by name containing and ignoring casing")
    @Transactional
    public void findByNameContainingIgnoreCase() {
        assertNotNull(gameService.findByName("one"), "Should return 'Game One'");
        assertNotNull(gameService.findByName("two"), "Should return 'Game two'");
    }
}
