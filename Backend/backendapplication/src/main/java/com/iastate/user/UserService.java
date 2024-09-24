package com.iastate.user;

import com.iastate.country.Country;
import com.iastate.country.CountryRepo;
import com.iastate.exceptions.UserAlreadyLoggedInException;
import com.iastate.player.Player;
import com.iastate.player.PlayerRepo;
import com.iastate.session.UserDetails;
import com.iastate.session.UserDetailsController;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gabriel Perez
 *
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    PlayerRepo pr;

    @Autowired
    CountryRepo cr;

    UserDetailsController udc;

    //CREATE
    public void register(User user){
        if(!isRegistered(user)) {
            userRepository.save(user);
        }
    }

    //READ
    public boolean isRegistered(User user){

        return userRepository.findByEmail(user.getEmail()) != null;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //UPDATE
    public void updateUser(User user){
        userRepository.save(user);
    }

    //DELETE
    public void deleteUserByUserId(Long id){
        userRepository.deleteById(id);
    }

    //EXTRA FUNCTIONALITY
    public boolean isValidLogin(String username, String password) {
        //check if session has already started (if th player is already there)
        System.out.println(pr.findByUsername(username));
    if(pr.findByUsername(username) != null) {
        for (Player p : pr.findAll()) {
            if (p.getUsername().equals(username)) {
                throw new UserAlreadyLoggedInException();

            }
        }
    }else{
        System.out.println(username + "  " + password);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("No such user");
            return false;
        }
        System.out.println("USER: " + user.getEmail());
        if(user.getPassword().equals(password)){
            System.out.println("logging in user");
            //have a match and user not logged in so create the player
            Player player = new Player();
            player.setUser(userRepository.findByUsername(username));
            player.setUsername(username);
            System.out.println("Player to be added: " + player.toString());

            //Optional<Country> c = cr.findById(1L);

            //player.setCountry(c.get());
            //System.out.println(player.getCountry().getGpt());
            pr.save(player);

            return true;
        }
    }


            return false;
    }

    public boolean logout(HttpSession session){ //Long id
        //correct this so it checks if they are in this list



        for(Player p : pr.findAll()){
            if(p.getUser().getId().equals(session.getAttribute("userId"))){
                //delete the player as well since the user is logging out

                pr.delete(p);

                return true;
            }
        }


    /*
        for(Player p : pr.findAll()){
            if(p.getUser().getUsername().equals(currentUserName)){
                pr.delete(p);
                return true;
            }
        }
    */
        return false;
    }


    public HttpSession getSession(HttpSession session){
        return session;
    }
}
