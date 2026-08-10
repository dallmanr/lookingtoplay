package com.dallman.lookingforgame.Controller;

import com.dallman.lookingforgame.Service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
}
