package com.dallman.lookingtoplay.Controller;

import com.dallman.lookingtoplay.DTO.IgdbGame;
import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Service.GameService;
import com.dallman.lookingtoplay.Service.IgdbGamesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private GameService gameService;
    private IgdbGamesClient igdbGamesClient;

    @Autowired
    public GameController(GameService gameService, IgdbGamesClient igdbGamesClient) {
        this.gameService = gameService;
        this.igdbGamesClient = igdbGamesClient;
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name="name") String name) {
        List<Game> games = gameService.findByName(name);
        if (games == null || games.size() == 0) {
            model.addAttribute("games", new ArrayList<Game>());
        } else {
            model.addAttribute("games", games);
        }
        return "searchgame";
    }

    @GetMapping("/addnewgame")
    public String searchForNewGame(@RequestParam(name="name") String name, Model model) {
        List<IgdbGame> response = igdbGamesClient.searchGameName(name);
        model.addAttribute("response", response);
        return "addnewgame";
    }

    @GetMapping("/newgamedetails")
    public String newGameDetails(@RequestParam(name="id") int id, Model model) {
        IgdbGame igdbGame = igdbGamesClient.findById(id);
        model.addAttribute("igdbGame", igdbGame);
        return "newgamedetails";
    }

    @GetMapping("/viewgamedetails")
    public String viewGameDetails(@RequestParam(name="id") int id, Model model)  {
        Game game = gameService.findById(id);
        model.addAttribute("game", game);
        return "gamedetails";

    }

    @PostMapping("/savegame")
    public String saveNewGame(@ModelAttribute IgdbGame igdbGame, Model model) {
        Game game = gameService.save(igdbGame);
        return viewGameDetails(game.getId(), model);
    }

}
