package com.iastate.server;

import com.iastate.player.Player;
import com.iastate.serversetting.ServerSettings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "ServerController",  description = "REST APIs related to Player Entity!!!!")
@RestController
public class ServerController {

    @Autowired
    private ServerService ss;

    //CREATE
    @ApiOperation(value = "Create a new Server in the System by players current Session", response = Iterable.class, tags = "createServer")
    @PostMapping("/Server/create")
    public void createServer(@RequestBody ServerSettings sss){
        ss.createServer(sss);
    }

    //READ
    @ApiOperation(value = "Get specific Server in the System by ID ", response = Iterable.class, tags = "getServerById")
    @PutMapping("/Server/getplayers/{serverId}")
    public List<Player> getPlayers(@PathVariable Long serverId){
        return ss.getPlayerList(serverId);
    }

    @ApiOperation(value = "Get all Servers in the system ", response = Iterable.class, tags = "getAllServers")
    @GetMapping("/Server")
    public List<Server> getAllServers(){
        return ss.getAllServers();
    }
    //UPDATE
    @ApiOperation(value = "Update a Server in the System by players current Session", response = Iterable.class, tags = "updateServer")
    @PostMapping("/Server/update")
    public void updateServer(@RequestBody Server server){
        ss.updateServer(server);
    }

    //DELETE
    @ApiOperation(value = "Delete a Server by ID", response = Iterable.class, tags = "deleteServerByID")
    @DeleteMapping("/Server/delete/{serverId}")
    public void deleteServer(@PathVariable Long serverId){
        ss.deleteServer(serverId);
    }


    //EXTRA FUNCTIONALITY
    @ApiOperation(value = "Potentially deprecated method to add a player to a server", response = Iterable.class, tags = "addPlayer")
    @PutMapping("/Server/Join/{serverId}")
    public void addPlayer(@PathVariable Long serverId, HttpSession session){
        ss.addPlayer(serverId, session);
    }

    //set the country
    @ApiOperation(value = "Deprecated method to set the country of a player", response = Iterable.class, tags = "setCountry")
    @PutMapping("/Server/setCountry/{countryId}")
    public void setCountry(@PathVariable Long countryId, HttpSession session){
        ss.setCountry(countryId,session);
    }


}
