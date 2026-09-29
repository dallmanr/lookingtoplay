package com.dallman.lookingtoplay.Lobby;

import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Repository.LobbyRepository;
import com.dallman.lookingtoplay.Repository.UserRepository;
import com.dallman.lookingtoplay.Service.LobbyService;
import com.dallman.lookingtoplay.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/.env.test.properties") // I want to use an in-memory H2 Database for testing, so I don't affect 'prod' data
public class LobbyTests {

    @MockitoBean
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private LobbyService lobbyService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private GameRepository gameRepository;

    @AfterEach
    void cleanup() {
        lobbyRepository.deleteAll();
        userRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    @DisplayName("Create a new lobby and add several players")
    void createNewLobbyAndAddPlayers() {
        User owner = new User("Rich", new BCryptPasswordEncoder().encode("test123!"), 1, "owner@email.com");

        User playerOne = new User("playerOne", new BCryptPasswordEncoder().encode("test123!"), 1, "playerOne@email.com");
        User playerTwo = new User("playerTwo", new BCryptPasswordEncoder().encode("test123!"), 1, "playerTwo@email.com");

        Game hitman = new Game("hitman", "This is a hitman game", "hitman.com", 1);

        Lobby lobby = new Lobby(owner, "first lobby", "this is a hitman lobby");
        lobby.getPlayers().add(playerOne);
        lobby.getPlayers().add(playerTwo);
        lobby.setGame(hitman);

        Set<User> players = new HashSet<>(Set.of(playerOne, playerTwo));
        lobbyRepository.save(lobby);

        Optional<Lobby> tempLobby = lobbyRepository.findByLobbyNameIgnoreCase(lobby.getLobbyName());

        assertEquals("first lobby", lobbyRepository.findByLobbyNameIgnoreCase(lobby.getName()).map(Lobby::getName).orElse("Not found"), "Lobby should be 'first lobby'");
        assertEquals(owner.getUsername(), lobbyRepository.findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(owner.getUsername(), LobbyStatus.OPEN), "Owner should be be 'Rich'");
        assertEquals(hitman.getLobbies().getFirst(), lobby, "Lobby should be 'hitman'");


       assertIterableEquals(lobby.getPlayers(),players, "Lobby should have playerOne and playerTwo");
    }

    @Test
    @DisplayName("Own no more than 1 lobby")
    void ownNoMoreThanOneLobby() {

    }
}
