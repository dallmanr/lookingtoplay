package com.dallman.lookingforgame.Controller;

import com.dallman.lookingforgame.DTO.IgdbResponse;
import com.dallman.lookingforgame.Game.Game;
import com.dallman.lookingforgame.Service.GameService;
import com.dallman.lookingforgame.Service.IgdbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String search(@RequestParam(name="name") String name, Model model) {
        List<IgdbResponse> response = igdbApiService.searchGameName(name);
        System.out.println("game name " + name);
        System.out.println("response " + response);
        model.addAttribute("response", response);
        return "search";
    }

}
