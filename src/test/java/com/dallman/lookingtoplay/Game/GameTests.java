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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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


    @AfterEach
    public void afterEach() {
        System.out.println("Teardown Tests");

        // Remove game from db
        gameService.deleteById(0);
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
        MvcResult result = mockMvc.perform(post("/savegame").with(csrf())
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
    @DisplayName("Testing Platform creation")
    public void testPlatformCreation() throws Exception {
        System.out.println("Test Platform creation");
    }
}
