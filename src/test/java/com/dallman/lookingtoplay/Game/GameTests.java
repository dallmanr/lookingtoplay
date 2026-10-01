package com.dallman.lookingtoplay.Game;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Exception.GameAlreadyExistsException;
import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Service.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/.env.test.properties")
public class GameTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("insert into games(name, summary, url, game_type, " +
                "igdb_id, first_release_date, rating, cover_id, release_status)" +
                "values ('CS2', 'pew pew', 'cs2.com', 1, 1, 1347926400, 2.2, 12345, 1);");
    }

    @AfterEach
    public void afterEach() {
//        gameRepository.deleteAll();
        jdbcTemplate.execute("delete from games");
        jdbcTemplate.execute("delete from platforms");
        jdbcTemplate.execute("ALTER TABLE games ALTER COLUMN game_id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE platforms ALTER COLUMN id RESTART WITH 1");
    }

    /*
    * Console commands:
    * a. mvn clean test - Cleans and runs tests - Will also create code coverage report now JaCoCo has been added to POM
    * b. mvn site - HTML reports with SureFire. target/site/surefire/index.html
    * 1. Check what lobbies the game belongs to
    * 2. Check what platforms the game is on
    * 3. Check a game cannot be saved twice
    * */

    @Test
    @DisplayName("Checking a game cannot be saved twice with the same Igdb ID")
    public void cannotSaveGameTwice() {
        // An example Igdb Game
        // "values ('CS2', 'pew pew', 'cs2.com', 1, 1, 1347926400, 2.2, 12345, 1)"); - From our @BeforeEach for reference

        IgdbGame duplicateGame = getDuplicateGame();
        // Trying to add a game with the same ID should throw an exception
        assertThrows(GameAlreadyExistsException.class, () -> gameService.save(duplicateGame, duplicateGame.platforms()), "Shouldn't be able to add a game with the same Igdb ID");

        // We should only have one game in our db, from the @BeforeEach, so check nothing was actually persisted
        assertEquals(1, gameRepository.count());

        // Check that we can still insert a unique game
        IgdbGame uniqueGame = getUniqueGame();
        assertNotNull(gameService.save(uniqueGame, uniqueGame.platforms()), "Game should not be null");
        assertEquals(2, gameRepository.count());
    }

    @Test
    static IgdbGame getDuplicateGame() {
        IgdbGame igdbGame = new IgdbGame(1, "New game", "A Summary",
                1457654400L, 4.5, 123456, 2, "website.com", 1, new ArrayList<>(List.of(1)));

        return igdbGame;
    }

    @Test
    static IgdbGame getUniqueGame() {
        IgdbGame igdbGame = new IgdbGame(2, "Another new game", "A Summary for another new game",
                1457654400L, 2.5, 654321, 3, "website.com", 1, new ArrayList<>(List.of(1)));

        return igdbGame;
    }

}
