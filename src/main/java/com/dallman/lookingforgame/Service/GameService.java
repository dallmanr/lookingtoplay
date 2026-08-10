package com.dallman.lookingforgame.Service;

import com.dallman.lookingforgame.Game.Game;

import java.util.List;

public interface GameService {
    List<Game> findAll();
    Game findById(int id);
    Game save(Game game);
    Game deleteById(int id);

}
