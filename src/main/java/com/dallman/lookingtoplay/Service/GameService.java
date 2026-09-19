package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findById(Integer id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game findByPlatformId(int platformId) {
        return gameRepository.findByPlatforms(platformId);
    }

    public List<Game> findByName(String name) {
        List<Game> games = gameRepository.findByNameIgnoreCase(name);
        return games;
    }

    public List<Game> findAll() {
        List<Game> games = gameRepository.findAll();
        return games;
    }
}
