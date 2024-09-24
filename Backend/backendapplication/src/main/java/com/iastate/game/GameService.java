package com.iastate.game;

import com.iastate.player.Player;
import com.iastate.server.Server;
import com.iastate.server.ServerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class GameService {

    @Autowired
    private ServerRepo serverRepository;

    public Game startGame(Long serverId){
        Server server = serverRepository.findById(serverId).orElseThrow(() -> new RuntimeException("Server not found with ID: " + serverId));;
        //check if there are a enough players that have picked countries (readied up)
        List<Player> playerList = server.getPlayer();
        for(Player p : playerList){
            if(p.getCountry() == null){
                throw new RuntimeException("Not enough players to start the server");
            }
        }

        //create the game
        //now we can start the server
        return new Game(server);
    }



}
