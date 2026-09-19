package com.dallman.lookingforgame.Repository;

import com.dallman.lookingforgame.Lobby.Lobby;
import com.dallman.lookingforgame.Lobby.LobbyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LobbyRepository extends JpaRepository<Lobby,Integer> {

    List<Lobby> findByLobbyNameIgnoreCase(String lobbyName);

    Lobby findByLobbyNameIgnoreCaseAndLobbyStatusOPEN(String lobbyName, LobbyStatus lobbyStatus);

    List<Lobby> findByGameNameIgnoreCaseAndLobbyStatusOPEN(String gameName, LobbyStatus lobbyStatus);

    Lobby findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(String ownerName, LobbyStatus lobbyStatus);
}
