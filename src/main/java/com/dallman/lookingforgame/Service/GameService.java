package com.dallman.lookingforgame.Service;

import com.dallman.lookingforgame.Game.Game;
import com.dallman.lookingforgame.Repository.GameRepository;
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

//    public Game findByPlatformId(int platformId) {
//        return gameRepository.findGameByPlatformId(platformId);
//    }

    public Game findByName(String name) {
        Game theGame = gameRepository.findGameByName(name);
        return theGame;
    }

    public List<Game> findAll() {
        List<Game> games = gameRepository.findAll();
        return games;
    }
}
