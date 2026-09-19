package com.dallman.lookingtoplay.Controller;

import com.dallman.lookingtoplay.DTO.IgdbResponse;
import com.dallman.lookingtoplay.Game.Game;
import com.dallman.lookingtoplay.Service.GameService;
import com.dallman.lookingtoplay.Service.IgdbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GameController {

    private GameService gameService;
    private IgdbApiService igdbApiService;

    @Autowired
    public GameController(GameService gameService, IgdbApiService igdbApiService) {
        this.gameService = gameService;
        this.igdbApiService = igdbApiService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "index";
    }

    @GetMapping("/addnewgame")
    public String search(@RequestParam(name="name") String name, Model model) {
        List<IgdbResponse> response = igdbApiService.searchGameName(name);
        System.out.println("game name " + name);
        System.out.println("response " + response);
        model.addAttribute("response", response);
        return "newgamesearch";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name="name") String name) {
        List<Game> games = gameService.findByName(name);
        model.addAttribute("games", games);
        return "index";
    }

}
