package com.dallman.lookingtoplay.Repository;

import com.dallman.lookingtoplay.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * findBy... Spring will automatically determine what to find by so long as the method signature is findBy... This is a derived query
 * Appending additional information to the query can be achieved with the use of And...
 * */
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findByNameIgnoreCase(String name);

    List<Game> findAll();

    Game findByPlatforms(int platformId);
}
