package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Game.Platform;
import com.dallman.lookingtoplay.Repository.GameRepository;
import com.dallman.lookingtoplay.Repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final PlatformRepository platformRespository;

    public GameService(GameRepository gameRepository, PlatformRepository platformRespository) {
        this.gameRepository = gameRepository;
        this.platformRespository = platformRespository;
    }

    public Game findById(Integer id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game findByIgdbId(Integer id) {
        return gameRepository.findByIgdbId(id);
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

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public void deleteById(int id) {
        Optional<Game> result = gameRepository.findById(id);
        Game tempGame = null;
        if (result.isPresent()) {
            tempGame = result.get();
            gameRepository.delete(tempGame);
        }
    }

    public Game save(IgdbGame igdbGame) {
        Game game = new Game();
        game.setIgdbId(igdbGame.id());
        game.setName(igdbGame.name());
        game.setSummary(igdbGame.summary());
        game.setFirstReleaseDate(igdbGame.firstReleaseDate());
        game.setRating(igdbGame.totalRating());
        game.setCoverId(igdbGame.cover());
        game.setReleaseStatus(igdbGame.gameStatus());
        game.setGameType(igdbGame.gameType());
        game.setUrl(igdbGame.url());
        game.setGameType(igdbGame.gameType());

        for (Integer i : igdbGame.platforms()) {
            game.addPlatform(new Platform(i, "PC"));
        }

        platformRespository.save(game.getPlatforms().iterator().next());
        return gameRepository.save(game);
    }
}
