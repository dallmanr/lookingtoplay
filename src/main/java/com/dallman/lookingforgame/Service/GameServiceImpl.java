package com.dallman.lookingforgame.Service;

import com.dallman.lookingforgame.Game.Game;
import com.dallman.lookingforgame.dao.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService{

    private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(int id) {
        Optional<Game> result = gameRepository.findById(id);
        Game game = null;

        if (result.isPresent()) {
            game = result.get();
        } else {
            throw new RuntimeException("Game with id " + id + " not found");
        }
        return game;
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game deleteById(int id) {
        Optional<Game> result = gameRepository.findById(id);
        Game game = null;

        if(result.isPresent()) {
            game = result.get();
            gameRepository.delete(game);
        }  else {
            throw new RuntimeException("Game with id " + id + " not found");
        }
        return game;
    }
}
