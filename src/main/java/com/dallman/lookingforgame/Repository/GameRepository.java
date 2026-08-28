package com.dallman.lookingforgame.Repository;

import com.dallman.lookingforgame.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findGameByName(String name);

    List<Game> findAll();

//    Game findGameByPlatformId(int platformId);
}
