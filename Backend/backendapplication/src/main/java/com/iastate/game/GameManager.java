package com.iastate.game;
import com.iastate.player.Player;
import com.iastate.server.Server;
import com.iastate.server.ServerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class GameManager {

    @Autowired
    ServerRepo serverRepository;
    private final ConcurrentHashMap<Long, Game> activeGames;

    public GameManager() {
        activeGames = new ConcurrentHashMap<>();
    }


    public Game createGame(Long serverId){
        Server server = serverRepository.findById(serverId).orElseThrow(() -> new RuntimeException("Server not found with ID: " + serverId));;
        //check if there are a enough players that have picked countries (readied up)
        List<Player> playerList = server.getPlayer();
        for(Player p : playerList){
            if(p.getCountry() == null){
                throw new RuntimeException("Not enough players to start the server");
            }
        }

        //create the game
        Game game = new Game(server);
        //put it in the hashmap
        activeGames.put(server.getId(), game);

        return game;
    }

    public Game getGame(Long serverId) {
        return activeGames.get(serverId);
    }


}
