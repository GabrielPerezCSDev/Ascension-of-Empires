package com.iastate.game;


import com.iastate.board.Board;
import com.iastate.rules.Rules;
import com.iastate.tile.Tile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "GameController", description = "REST APIs related to Game Entity!!!!")
@RestController
@EnableAutoConfiguration
@RequestMapping("/Game")
public class GameController {

    @Autowired
    GameService gs;

    @Autowired
    Rules rules;
    @Autowired
    private GameManager gameManager;
    @ApiOperation(value = "Start a game from a specific server ", response = Iterable.class, tags = "createGame")
    @PutMapping("/startGame/{serverId}")
    public Game startGame(@PathVariable Long serverId){

        return gameManager.createGame(serverId);
    }

    @ApiOperation(value = "Get specific Game in the System by Server ID ", response = Iterable.class, tags = "getGameByServerId")
    @GetMapping("/Game/{serverId}")
    public Game getGame(@PathVariable Long serverId){
        return gameManager.getGame(serverId);
    }

    @ApiOperation(value = "Process players turn by Game attached to server ID ", response = Iterable.class, tags = "processTurn")
    @RequestMapping("/{serverId}/turn")
    public String turnController(@PathVariable("serverId") Long serverId, HttpSession session) {
        //get the game
        Game g = getGame(serverId);
        rules.playerTurn(session, g);
        return null;
    }
    @ApiOperation(value = "Process user purchasing item by Game attached to server ID ", response = Iterable.class, tags = "purchaseItem")
    @RequestMapping("/{serverId}/{itemId}/purchase")
    public void purchaseItem(@PathVariable("serverId") Long serverId, @PathVariable("itemId") Long itemId, HttpSession session){
       //get the game

        Game g = getGame(serverId);
        rules.buyItem(itemId, session, g);
    }
}
