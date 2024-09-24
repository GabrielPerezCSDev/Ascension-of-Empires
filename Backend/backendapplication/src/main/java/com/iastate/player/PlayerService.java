package com.iastate.player;
import com.iastate.country.CountryRepo;
import com.iastate.server.Server;
import com.iastate.server.ServerRepo;
import com.iastate.country.Country;
import com.iastate.serversetting.ServerSettings;
import com.iastate.serversetting.ServerSettingsRepo;
import com.iastate.session.UserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class PlayerService {

    @Autowired
    PlayerRepo pr;
    @Autowired
    ServerRepo sr;
    @Autowired
    CountryRepo cr;
    @Autowired
    ServerSettingsRepo ssr;


    //CREATE
    public void createPlayer(Player p){
        pr.save(p);
    }
    //READ
    public Player getPlayerById(Long id){
        return pr.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }

    public Player getPlayerByUserId(Long id){
        return pr.findByUserId(id);
    }

    public List<Player> getAllPlayers(){
        return pr.findAll();
    }

    public Player getPlayerByUsername(String username){return pr.findByUsername(username);}
    //UPDATE
    public void updatePlayer(Player p){
        pr.save(p);
    }

    public void pickCountry(Long cId){
        //add the country tot eh current player
        Player p = pr.findById(pr.findIdByUsername(UserDetails.username)).orElseThrow(() -> new EntityNotFoundException("Player not found"));
        p.setCountry(cr.findById(cId).orElseThrow(() -> new EntityNotFoundException("Country not found")));
        //save the player to the repository
        pr.save(p);
    }
    //DELETE
    public void deletePlayer(Player p){
        pr.delete(p);
    }

    public void deletePlayerByID(Long id){pr.deleteById(id);}


    //need more thought into these methods

    //create a method to join a server
    public void joinServer(Long serverId) {
        //first get the server
        Server s = sr.findById(serverId).orElseThrow(() -> new EntityNotFoundException("Server not found"));

        //get all the players in the server
        List<Player> pl = pr.findAllByServerId(serverId);
        //check if server is full
        ServerSettings ss = ssr.findById(s.getServerSettings().getId()).orElseThrow(() -> new EntityNotFoundException("Server Settings not found"));

        if (pl.size() < ss.getSize()) {
            //get the current player
            Player p = pr.findByUserId(UserDetails.userId);
            //update the player to have the new server
            p.setServer(s);
            //save the player to the repository
            pr.save(p);
            //add to server list??
            s.getPlayer().add(p);
        }
    }

    public List<Player> getAllPlayersInServer(Long serverId){
        return pr.findAllByServerId(serverId);
    }

}
