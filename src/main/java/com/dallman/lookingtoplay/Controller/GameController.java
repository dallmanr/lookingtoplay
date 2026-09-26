package com.dallman.lookingtoplay.Controller;

import com.dallman.lookingtoplay.DTO.IgdbResponse;
import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Service.GameService;
import com.dallman.lookingtoplay.Service.IgdbGamesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String search(@RequestParam(name="name") String name, Model model) {
        List<IgdbResponse> response = igdbGamesClient.searchGameName(name);
        System.out.println("game name " + name);
        System.out.println("response " + response);
        model.addAttribute("response", response);
        return "addnewgame";
    }

    @GetMapping("/gamedetails")
    public String viewGameDetails(@RequestParam(name="id") int id, Model model) {
        // Call the API again, this time using the game ID and display the results
        System.out.println("game id " + id);
        IgdbResponse igdbResponse = igdbGamesClient.findById(id);
        System.out.println(igdbResponse);
        model.addAttribute("igdbResponse", igdbResponse);
        return "gamedetails";
    }

}
