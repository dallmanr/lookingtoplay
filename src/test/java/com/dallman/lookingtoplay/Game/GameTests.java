package com.dallman.lookingtoplay.Game;


import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Service.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameTests {

    private static MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    // Use the ObjectMapper because our /savegame uses @RequestBody which is going to be our IgdbResponse we create a game from
    // We can then pass our test object in our test for creating a game
    @Autowired
    private ObjectMapper objectMapper;

    // Find game by name

    // Find game by ID

    // Check if game is on platform

    // Find all lobbies for game

    // Create game from IgdbResponse

//    @BeforeAll
//    public static void setup() {
//
//    }

//    @BeforeEach
//    public void beforeEach() {
//        System.out.println("Setting up Tests");
//
//    }

    @AfterEach
    public void afterEach() {
        System.out.println("Teardown Tests");

        // Remove game from db
        gameService.deleteById(0);
    }

    @Test
    @WithMockUser(username="user", roles = {"USER"}) // Our page requires a user to be logged in so we setup a mock user
    @DisplayName("Testing creating a game from HTTP Request")
    public void createGameFromIgdbResponse() throws Exception {
        System.out.println("Create a game from Igdb Response");

        IgdbGame igdbGame = new IgdbGame(0, "Hitman", "Hitman is a stealth game...", 1457654400L, 4.5, 113355, 8, "website.com", 1, new ArrayList<Integer>());
        igdbGame.platforms().add(new Integer(1));

        // Our endpoint, /savegame, takes an Igdb Response entity that a user has searched for and selected as the game to add
        // So when we save the game, we take the igdb response entity, and create a Game entity based on its values
        // We can pass the igdb response from this test into the content of our post
        MvcResult mvcResult = this.mockMvc.perform(post("/savegame").with(csrf())// Match my end point!
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(igdbGame)))
                .andExpect(status().isOk())
                .andReturn();


        ModelAndView mav = mvcResult.getModelAndView();
        // Once we successfully create the game, the user should be taken to the page to view the details for the given game
        // From here, they could go on to create a lobby if required
        ModelAndViewAssert.assertViewName(mav, "gamedetails");

        // The game should have been saved, so check that it exists
        Optional<Game> verifyGame = gameRepository.findByIgdbId(igdbGame.id());
        assertNotNull(verifyGame, "Game should not be null");
        assertNotNull(gameService.findByName(verifyGame.get().getName()), "Game should not be null");
//        assertIterableEquals(igdbResponse.platforms(),  verifyGame.getPlatforms(), "Platforms should match");

    }

    @Test
    @DisplayName("Testing Platform creation")
    public void testPlatformCreation() throws Exception {
        System.out.println("Test Platform creation");
    }
}
