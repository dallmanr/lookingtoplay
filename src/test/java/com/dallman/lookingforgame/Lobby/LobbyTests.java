package com.dallman.lookingforgame.Lobby;

import com.dallman.lookingforgame.Game.Game;
import com.dallman.lookingforgame.Repository.GameRepository;
import com.dallman.lookingforgame.Repository.LobbyRepository;
import com.dallman.lookingforgame.Repository.UserRepository;
import com.dallman.lookingforgame.Service.LobbyService;
import com.dallman.lookingforgame.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
public class LobbyTests {

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
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
        User owner = new User("owner_rich_a", new BCryptPasswordEncoder().encode("test123!"), 1, "owner@email.com");

        User playerOne = new User("playerOneB", new BCryptPasswordEncoder().encode("test123!"), 1, "playerOne@email.com");
        User playerTwo = new User("playerTwoC", new BCryptPasswordEncoder().encode("test123!"), 1, "playerTwo@email.com");

        Game hitman = new Game("hitman", "This is a hitman game", "hitman.com", 1);

        Lobby lobby = new Lobby(owner, "first lobby", "this is a hitman lobby");
        lobby.getPlayers().add(playerOne);
        lobby.getPlayers().add(playerTwo);
        lobby.setGame(hitman);

        Set<User> players = new HashSet<>(Set.of(playerOne, playerTwo));
        lobbyRepository.save(lobby);

       assertIterableEquals(lobby.getPlayers(),players, "Lobby should have playerOne and playerTwo");
    }

    @Test
    @DisplayName("Own no more than 1 lobby")
    void ownNoMoreThanOneLobby() {

    }
}
