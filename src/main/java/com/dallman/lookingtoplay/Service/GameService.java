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

    private final PlatformService platformService;

    public GameService(GameRepository gameRepository, PlatformRepository platformRespository, PlatformService platformService) {
        this.gameRepository = gameRepository;
        this.platformRespository = platformRespository;
        this.platformService = platformService;
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
        if (gameRepository.findByIgdbId(igdbGame.id()).isPresent()) {
            throw new RuntimeException("Game already exists");
        }

        Game game = gameRepository.findByIgdbId(igdbGame.id())
                .orElseGet(() -> {
                    Game newGame = new Game();
                    newGame.setIgdbId(igdbGame.id());
                    newGame.setName(igdbGame.name());
                    newGame.setSummary(igdbGame.summary());
                    newGame.setFirstReleaseDate(igdbGame.firstReleaseDate());
                    newGame.setRating(igdbGame.totalRating());
                    newGame.setCoverId(igdbGame.cover());
                    newGame.setReleaseStatus(igdbGame.gameStatus());
                    newGame.setGameType(igdbGame.gameType());
                    newGame.setUrl(igdbGame.url());

                    return gameRepository.save(newGame);
                });

        List<Platform> platforms = platformService.findOrCreateByIgdbIds(platformIds);
        for (Platform platform : platforms) {
            platform.addGame(game);
        }
        platformRespository.saveAll(platforms);
        game.setPlatforms(platforms);
        return gameRepository.save(game);
    }
}
