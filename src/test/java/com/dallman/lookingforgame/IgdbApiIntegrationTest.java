package com.dallman.lookingforgame;

import com.dallman.lookingforgame.DTO.IgdbResponse;
import com.dallman.lookingforgame.Service.IgdbApiService;
import com.dallman.lookingforgame.Service.IgdbOAuthClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IgdbApiIntegrationTest {

    @Autowired
    private IgdbApiService igdbApiService;

    @Autowired
    private IgdbOAuthClient igdbOAuthClient;

    @Test
    void testFlow() {
        System.out.println("### TESTING: testFlow() ###");
        String accessToken = igdbOAuthClient.getAccessToken();
        System.out.println("Token obtained: " + accessToken.substring(0,10));

        IgdbResponse game = igdbApiService.searchGameName("Rival Species");

        assertNotNull(game);
        assert(game.name().length() > 0);
        System.out.println("Found game: " + game.name());
    }
}
