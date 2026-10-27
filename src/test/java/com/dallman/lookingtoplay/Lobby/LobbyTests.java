package com.dallman.lookingtoplay.lobby;

import com.dallman.lookingtoplay.game.Game;
import com.dallman.lookingtoplay.repository.GameRepository;
import com.dallman.lookingtoplay.repository.LobbyRepository;
import com.dallman.lookingtoplay.repository.UserRepository;
import com.dallman.lookingtoplay.service.LobbyService;
import com.dallman.lookingtoplay.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
        User owner = new User("Rich", new BCryptPasswordEncoder().encode("test123!"), true, "owner@email.com");

        User playerOne = new User("playerOne", new BCryptPasswordEncoder().encode("test123!"), true, "playerOne@email.com");
        User playerTwo = new User("playerTwo", new BCryptPasswordEncoder().encode("test123!"), true, "playerTwo@email.com");

        Game hitman = new Game("hitman", "This is a hitman game", "hitman.com", 1);

        Lobby lobby = new Lobby(owner, "first lobby", "this is a hitman lobby");
        lobby.getPlayers().add(playerOne);
        lobby.getPlayers().add(playerTwo);
        lobby.setGame(hitman);

        Set<User> players = new HashSet<>(Set.of(playerOne, playerTwo));
        lobbyRepository.save(lobby);

        Optional<Lobby> tempLobby = lobbyRepository.findByLobbyNameIgnoreCase(lobby.getLobbyName());

        assertEquals("first lobby", lobbyRepository.findByLobbyNameIgnoreCase(lobby.getLobbyName()).map(Lobby::getLobbyName).orElse("Not found"), "Lobby should be 'first lobby'");
        assertEquals(owner.getUsername(), lobbyRepository.findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(owner.getUsername(), LobbyStatus.OPEN), "Owner should be be 'Rich'");
        assertEquals(hitman.getLobbies().getFirst(), lobby, "Lobby should be 'hitman'");


       assertIterableEquals(lobby.getPlayers(),players, "Lobby should have playerOne and playerTwo");
    }

    @Test
    @DisplayName("Own no more than 1 lobby")
    void ownNoMoreThanOneLobby() {

    }
}
