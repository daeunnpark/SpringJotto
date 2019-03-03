package com.white.demo.database.GameController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;


import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import com.white.demo.database.model.GameLog;
import com.white.demo.database.model.Game;
import com.white.demo.database.model.Login;
import com.white.demo.database.Repositories.GameLogRepository;
import com.white.demo.database.LoginController.LoginController;
import java.util.ArrayList;

import com.white.demo.database.Repositories.GameRepository;
import com.white.demo.database.Repositories.LoginRepository;
import java.util.Date;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/game") // This means URL's start with /demo (after Application path)
public class GameController {
    @Autowired // This means to get the bean called userRepository
    private GameRepository gameRepository;
    //@Autowired
    //private LoginRepository loginRepository;
    //@Autowired
    //private GameLogRepository logRepository;
    public static int gamecount = 0;

    //Date d = new Date();

    Date d = new Date();

    @GetMapping(path="/addGame") // Map ONLY GET Requests
    public @ResponseBody String addNewGame (@RequestParam int game_ID,
                                            @RequestParam String Player1, @RequestParam String Player2) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request


        Game g = new Game();
        g.setGame_ID(game_ID);
        g.setPlayer1(Player1);
        g.setPlayer2(Player2);
        g.setDate(d.toGMTString());
        this.gameRepository.save(g);

        return "GameCount: " + gamecount++;

    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public
    ModelAndView
    showAll() {
        ModelAndView mv =  new ModelAndView("redirect:/history");
        //System.out.println(gameRepository.findAll().getClass());
        List<Game> g = gameRepository.findByplayer1(LoginController.name);
        mv.addObject("games", g);
        // for (Game g :  gameRepository.findByplayer1(LoginController.name)){
        //    System.out.println(g.getPlayer1());
        // }
        return mv;
    }


    @GetMapping(path="/allGame")
    public @ResponseBody Iterable<Game> getAllGame() {
        // This returns a JSON or XML with the users
        return gameRepository.findAll();
    }


}