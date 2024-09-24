package com.iastate.server;

import com.iastate.country.Country;
import com.iastate.country.CountryRepo;
import com.iastate.player.Player;
import com.iastate.player.PlayerRepo;
import com.iastate.serversetting.ServerSettings;
import com.iastate.session.UserDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
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
public class ServerService {
    @Autowired
    ServerRepo sr;

    @Autowired
    PlayerRepo pr;

    @Autowired
    CountryRepo cr;


    //CREATE
    public void createServer(ServerSettings ss){
        Server s = new Server();
        s.setServerSettings(ss);
        sr.save(s);
    }

    //READ
    public List<Player> getPlayerList(Long serverId){
        Server s = sr.findById(serverId).orElseThrow(() -> new EntityNotFoundException("Server not found"));
        return s.getPlayer();
    }

    public List<Server> getAllServers(){
        return sr.findAll();
    }

    //UPDATE
    public void updateServer(Server server){
        sr.save(server);
    }
    //DELETE
    public void deleteServer(Long serverId){
        sr.deleteById(serverId);
    }

    //EXTRA FUNCTIONALITY
    public void addPlayer(Long serverId, HttpSession session){
        System.out.println();
        System.out.println("Adding "+ pr.findByUserId((Long) session.getAttribute("userId")) +" to server "+ serverId);
        //first get the list of current players in the server
        Server s = sr.findById(serverId).orElseThrow(() -> new EntityNotFoundException("Server not found"));
        List<Player> p =  s.getPlayer();
        System.out.println("Current number of players in server: " + p.size());
        //check if server is at max capacity
        ServerSettings ss = s.getServerSettings();
        System.out.println("Server size: " + ss.getSize());
        if(p.size() < ss.getSize()){
            System.out.println("Server is not at max capacity");
            //add player to the list


            //-------------------------------------------------//
            //use the HTTP injection guy
            Player player = pr.findByUserId((Long) session.getAttribute("userId"));
            System.out.println("Player to be added: " + player.getUsername());
            p.add(pr.findByUserId((Long) session.getAttribute("userId")));

            //update the player to have the new server
            player.setServer(s);
            pr.save(player);
            //update the server to hvae the new list of players
            s.setPlayer(p);
            sr.save(s);
        }else{
            //server is at max capacity
            System.out.println("Server is at max capacity");
        }
    }

    public void setCountry(Long countryId, HttpSession session){
        //grab the country
        Optional<Country> c = cr.findById(countryId);
        if (c.isPresent()) {
            Player p = pr.findByUserId((Long) session.getAttribute("userId"));
            //set the country for the user
            p.setCountry(c.get());
            System.out.println();
            System.out.println(p.getUsername()+" has been chosen "+c.get().getName());
            //save the player
            pr.save(p);
        } else {
            throw new RuntimeException("Country not found");
        }

    }


}
