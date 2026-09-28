package com.dallman.lookingtoplay.Service;


import com.dallman.lookingtoplay.Lobby.Lobby;
import com.dallman.lookingtoplay.Lobby.LobbyStatus;
import com.dallman.lookingtoplay.Repository.LobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;


    public LobbyService(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public Lobby findById(int id) {
        return lobbyRepository.findById(id).orElse(null);
    }

    public Optional<Lobby> findByLobbyNameIgnoreCaseAndLobbyStatusOpen(String lobbyName) {
        return lobbyRepository.findByLobbyNameIgnoreCaseAndLobbyStatusOPEN(lobbyName, LobbyStatus.OPEN);
    }

    public Optional<List<Lobby>> findByGameNameIgnoreCase(String lobbyName) {
        return lobbyRepository.findByLobbyNameIgnoreCase(lobbyName);
    }

    public Optional<List<Lobby>> findByGameNameIgnoreCaseOpenOnly(String gameName) {
        return lobbyRepository.findByGameNameIgnoreCaseAndLobbyStatusOPEN(gameName, LobbyStatus.OPEN);
    }

    public Optional<Lobby> findByOwnerUserNameIgnoreCaseOpenOnly(String ownerName) {
        return lobbyRepository.findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(ownerName, LobbyStatus.OPEN);
    }

}
