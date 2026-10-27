package com.dallman.lookingtoplay.controller;

import com.dallman.lookingtoplay.dto.IgdbGame;
import com.dallman.lookingtoplay.dto.IgdbPlatform;
import com.dallman.lookingtoplay.game.Game;
import com.dallman.lookingtoplay.service.GameService;
import com.dallman.lookingtoplay.service.IgdbGamesClient;
import com.dallman.lookingtoplay.service.IgdbPlatformsClient;
import com.dallman.lookingtoplay.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/games")
public class GameController {

    private GameService gameService;
    private PlatformService platformService;
    private IgdbGamesClient igdbGamesClient;
    private IgdbPlatformsClient igdbPlatformsClient;

    @Autowired
    public GameController(GameService gameService, PlatformService platformService,
                          IgdbGamesClient igdbGamesClient,  IgdbPlatformsClient igdbPlatformsClient) {
        this.gameService = gameService;
        this.igdbGamesClient = igdbGamesClient;
        this.platformService = platformService;
        this.igdbPlatformsClient = igdbPlatformsClient;
    }


    // The landing page - Show all current games in our database
    @RequestMapping("/")
    public String index(Model model) {
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "index";
    }

    // Search our games database for a game with the given name
    @GetMapping("/search")
    public String search(Model model, @RequestParam(name="name") String name) {
        Optional<List<Game>> games = gameService.findByName(name);
        if (!games.isPresent()) {
            model.addAttribute("games", new ArrayList<Game>());
        } else {
            model.addAttribute("games", games.get());
        }
        return "/games/searchgame";
    }

    // When search does not return a game, the user can then search IGDB for the game(s) by that name
    @GetMapping("/addnewgame")
    public String searchForNewGame(@RequestParam(name="name") String name, Model model) {
        List<IgdbGame> response = igdbGamesClient.searchGameName(name);
        if  (response != null && !response.isEmpty()) {
            model.addAttribute("response", response);
        }
        return "/games/addnewgame";
    }

    // When a user has searched IGDB for a Game to add, they can view the details of it before it gets saved
    @GetMapping("/newgamedetails")
    public String newGameDetails(@RequestParam(name="id") int id, Model model) {
        IgdbGame igdbGame = igdbGamesClient.findById(id);
        if (igdbGame != null) {
            model.addAttribute("igdbGame", igdbGame);
            List<IgdbPlatform> platforms = igdbPlatformsClient.getPlatformInfo(igdbGame.platforms());
            if (platforms != null && !platforms.isEmpty()) {
                model.addAttribute("platforms", platforms);
            }
        }
        return "/games/newgamedetails";
    }

    // For viewing details of already existing games in our database
    @GetMapping("/viewgamedetails")
    public String viewGameDetails(@RequestParam("id")int id, Model model)  {
        Game game = gameService.findById(id);
        if (game != null) {
            model.addAttribute("game", game);
            Long epochSeconds  = game.getFirstReleaseDate();
            String firstReleaseDate = epochSeconds == null ? "?" : epochSeconds.toString();
            model.addAttribute("firstReleaseDate", firstReleaseDate);
        }
        return "/games/viewgamedetails";

    }

    // This is for saving the Igdb Game to our database. We map the fields the necessary fields and create the relevant platforms,
    // so long as they do not already exist!
    @PostMapping("/savegame")
    public String saveNewGame(@ModelAttribute IgdbGame igdbGame, @RequestParam(name="platformIds", required=false) List<Integer> platformIds, Model model) {
        Game game = gameService.save(igdbGame,  platformIds);
        return ("redirect:/viewgamedetails/" + game.getId());
    }

}
