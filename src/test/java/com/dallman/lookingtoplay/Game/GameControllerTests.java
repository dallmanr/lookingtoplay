package com.dallman.lookingtoplay.Game;


import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Exception.GameAlreadyExistsException;
import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Service.GameService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/.env.test.properties")
// I want to use an in-memory H2 Database for testing, so I don't affect 'prod' data
public class GameControllerTests {

    private static MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Use the ObjectMapper because our /savegame uses @RequestBody which is going to be our IgdbResponse we create a game from
    // We can then pass our test object in our test for creating a game
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * 1. View game details
     * 2. Searching for game - exact matches and fuzzy matching - Throwing exception when not found
     * 3. Not being able to add a game that already exists / no option to add
     * */

    @BeforeAll
    static void setup() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

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

    @Test
    @WithMockUser(username = "user", roles = "USER") // Users need to be logged in to save a game so use a mock user
    @DisplayName("Saving a game from an IGDB response persists it and shows its details")
    void createGameFromIgdbResponse() throws Exception {
        // Create our example game from the API response
        IgdbGame igdbGame = new IgdbGame(0, "Hitman", "Hitman is a stealth game...",
                1457654400L, 4.5, 113355, 8, "website.com", 1, new ArrayList<>(List.of(1)));

        // Attempt to save the game. A successful save gives us a redirect to /viewgamedetails/{id}
        // SO we check for the status.is3xxRedirection HTTP status
        MvcResult result = mockMvc.perform(post("/games/savegame").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(igdbGame)))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Now we check if the game has been successfully saved or throw an exception if its missing
        Game saved = gameRepository.findByIgdbId(igdbGame.id())
                .orElseThrow(() -> new AssertionError("Game was not saved"));

        // Check the game name matches
        assertEquals("Hitman", saved.getName());
        // Check that url we end up on is correct '/viewgamedetails/{id}'
        assertEquals("/viewgamedetails/" + saved.getId(), result.getResponse().getRedirectedUrl());
    }

    @Test
    @WithMockUser
    @DisplayName("Testing the same game, based on Igdb ID cannot be added twice")
    void cannotAddGameTwice() throws Exception {
//        "values ('CS2', 'pew pew', 'cs2.com', 1, 1, 1347926400, 2.2, 12345, 1)");
        IgdbGame igdbGame = new IgdbGame(1, "New game", "A Summary",
                1457654400L, 4.5, 113355, 8, "website.com", 1, new ArrayList<>(List.of(1)));

        mockMvc.perform(post("/games/savegame")
                        .with(csrf())
                        .param("platformIds", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(igdbGame)))
                .andExpect(status().isOk())
                .andExpect(view().name("/games/addnewgame"))
                .andExpect(model().attributeExists("error"));
    }
}
