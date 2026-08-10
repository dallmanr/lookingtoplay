package com.dallman.lookingforgame.dao;

import com.dallman.lookingforgame.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
