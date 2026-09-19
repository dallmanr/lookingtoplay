package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Lobby.Lobby;
import com.dallman.lookingtoplay.Lobby.LobbyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/*
* findBy... Spring will automatically determine what to find by so long as the method signature is findBy... This is a derived query
* Appending additional information to the query can be achieved with the use of And...
* */
public interface LobbyRepository extends JpaRepository<Lobby,Integer> {

    List<Lobby> findByLobbyNameIgnoreCase(String lobbyName);

    Lobby findByLobbyNameIgnoreCaseAndLobbyStatusOPEN(String lobbyName, LobbyStatus lobbyStatus);

    List<Lobby> findByGameNameIgnoreCaseAndLobbyStatusOPEN(String gameName, LobbyStatus lobbyStatus);

    Lobby findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(String ownerName, LobbyStatus lobbyStatus);
}
