package com.dallman.lookingtoplay;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Service.IgdbGamesClient;
import com.dallman.lookingtoplay.Service.IgdbOAuthClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/.env.test.properties") // I want to use an in-memory H2 Database for testing, so I don't affect 'prod' data
public class IgdbApiIntegrationTest {

    @Autowired
    private IgdbGamesClient igdbGamesClient;

    @Autowired
    private IgdbOAuthClient igdbOAuthClient;

    @Test
    void testFlow() {
        System.out.println("### TESTING: testFlow() ###");
        String accessToken = igdbOAuthClient.getAccessToken();
        System.out.println("Token obtained: " + accessToken.substring(0,10));

        List<IgdbGame> game = igdbGamesClient.searchGameName("Rival Species");

        assertNotNull(game);
        assert(game.getFirst().name().length() > 0);
        System.out.println("Found game: " + game.getFirst().name());
    }
}
