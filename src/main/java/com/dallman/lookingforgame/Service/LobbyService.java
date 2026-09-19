package com.dallman.lookingforgame.Service;


import com.dallman.lookingforgame.Lobby.Lobby;
import com.dallman.lookingforgame.Lobby.LobbyStatus;
import com.dallman.lookingforgame.Repository.LobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;


    public LobbyService(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public Lobby findById(int id) {
        return lobbyRepository.findById(id).orElse(null);
    }

    public Lobby findByLobbyNameIgnoreCaseAndLobbyStatusOpen(String lobbyName) {
        return lobbyRepository.findByLobbyNameIgnoreCaseAndLobbyStatusOPEN(lobbyName, LobbyStatus.OPEN);
    }

    public List<Lobby> findByGameNameIgnoreCase(String lobbyName) {
        return lobbyRepository.findByLobbyNameIgnoreCase(lobbyName);
    }

    public List<Lobby> findByGameNameIgnoreCaseOpenOnly(String gameName) {
        return lobbyRepository.findByGameNameIgnoreCaseAndLobbyStatusOPEN(gameName, LobbyStatus.OPEN);
    }

    public Lobby findByOwnerUserNameIgnoreCaseOpenOnly(String ownerName) {
        return lobbyRepository.findByOwnerNameIgnoreCaseAndLobbyStatusOPEN(ownerName, LobbyStatus.OPEN);
    }

}
