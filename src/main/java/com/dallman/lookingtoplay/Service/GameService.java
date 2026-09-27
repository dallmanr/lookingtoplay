package com.dallman.lookingtoplay.Service;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.DTO.IgdbPlatform;
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

    private final PlatformService platformService;

    public GameService(GameRepository gameRepository, PlatformRepository platformRespository, PlatformService platformService) {
        this.gameRepository = gameRepository;
        this.platformRespository = platformRespository;
        this.platformService = platformService;
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

    public Game save(IgdbGame igdbGame, List<Integer> platformIds) {
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

        List<Platform> platforms = platformService.findOrCreateByIgdbIds(platformIds);
        for (Platform platform : platforms) {
            platform.addGame(game);
        }
        platformRespository.saveAll(platforms);
        game.setPlatforms(platforms);
        return gameRepository.save(game);
    }
}
