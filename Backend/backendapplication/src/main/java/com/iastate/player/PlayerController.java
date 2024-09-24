package com.iastate.player;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "PlayerController", description = "REST APIs related to Player Entity!!!!")
@RestController
@EnableAutoConfiguration
public class PlayerController {

    @Autowired
    PlayerService ps;

    @ApiOperation(value = "Get list of Players in the System ", response = Iterable.class, tags = "getPlayers")
    @GetMapping("/Player")
    public List<Player> getAllPlayers(){
        return ps.getAllPlayers();
    }

    @ApiOperation(value = "Player in the session pick their country ", response = Iterable.class, tags = "setPlayerCountry")
    @PutMapping("/Player/pickcountry/{countryId}")
    public void pickCountry(@PathVariable Long countryId){
        ps.pickCountry(countryId);
    }

    @ApiOperation(value = "Player in the session joins a server " , response = Iterable.class, tags = "addPlayerToServer")
    @PutMapping("/Player/joinserver/{serverId}")
    public void addPlayerToServer(@PathVariable Long serverId){
        ps.joinServer(serverId);
    }

    @ApiOperation(value = "Get the players in a specific server ", response = Iterable.class, tags = "getAllPlayersByServer")
    @PutMapping("{serverId}/players")
    public List<Player> getAllPlayersByServers(@PathVariable Long serverId){
        return ps.getAllPlayersInServer(serverId);
    }

    @ApiOperation(value = "Get specific Player in the System by username ", response = Iterable.class, tags = "getPlayerByUsername")
    @PutMapping("/player/{username}")
    public Player getPlayerByUsername(@PathVariable String username){
        return ps.getPlayerByUsername(username);
    }

    @ApiOperation(value = "Delete player by ID ", response = Iterable.class, tags = "deletePlayerById")
    @PutMapping("/player/delete/{id}")
    public void deletePlayerById(@PathVariable Long id){
        ps.deletePlayerByID(id);
    }

    @PostMapping("/player/create")
    public void createPlayer(Player player){
        ps.createPlayer(player);
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable Long id) {
        return ps.getPlayerById(id);
    }

    }
