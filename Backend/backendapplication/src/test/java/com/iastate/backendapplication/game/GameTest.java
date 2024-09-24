package com.iastate.backendapplication.game;

import com.iastate.country.Country;
import com.iastate.player.Player;
import com.iastate.server.Server;
import com.iastate.game.Game;
import com.iastate.user.User;
import com.iastate.serversetting.ServerSettings;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    //player and server settings into server
    List<Player> playerList1 = new ArrayList<>();
    List<Player> playerList2 = new ArrayList<>();
    List<Player> playerList3 = new ArrayList<>();

    //server settings
    ServerSettings ss1 = new ServerSettings();
    ServerSettings ss2 = new ServerSettings();
    ServerSettings ss3 = new ServerSettings();


    //three servers
    Server server1 = new Server();
    Server server2 = new Server();
    Server server3 = new Server();

    @Before
    public void setUp(){
        //generate the three server settings
        ss1.setTurnLimit(10);
        ss1.setName("game1");
        ss1.setSize(3);

        ss2.setTurnLimit(10);
        ss2.setName("game2");
        ss2.setSize(4);

        ss3.setTurnLimit(10);
        ss3.setName("game3");
        ss3.setSize(6);

        //generate 6 countries
        Country country1 = new Country();
        country1.setName("country1");
        country1.setBuildings(1);
        country1.setGpt(1);
        country1.setGrwpt(1);
        country1.setUnits(2);

        Country country2 = new Country();
        country2.setName("country2");
        country2.setBuildings(2);
        country2.setGpt(3);
        country2.setGrwpt(2);
        country2.setUnits(3);

        Country country3 = new Country();
        country3.setName("country3");
        country3.setBuildings(3);
        country3.setGpt(1);
        country3.setGrwpt(1);
        country3.setUnits(2);

        Country country4 = new Country();
        country4.setName("country4");
        country4.setBuildings(1);
        country4.setGpt(1);
        country4.setGrwpt(1);
        country4.setUnits(2);

        Country country5 = new Country();
        country5.setName("country5");
        country5.setBuildings(1);
        country5.setGpt(1);
        country5.setGrwpt(1);
        country5.setUnits(2);

        Country country6 = new Country();
        country6.setName("country6");
        country6.setBuildings(1);
        country6.setGpt(1);
        country6.setGrwpt(1);
        country6.setUnits(2);

        Country country7 = new Country();
        country7.setName("country7");
        country7.setBuildings(1);
        country7.setGpt(1);
        country7.setGrwpt(1);
        country7.setUnits(2);

        //generate six users to attact to the players
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("password3");

        User user4 = new User();
        user4.setUsername("user4");
        user4.setPassword("password4");

        User user5 = new User();
        user5.setUsername("user5");
        user5.setPassword("password5");

        User user6 = new User();
        user6.setUsername("user6");
        user6.setPassword("password6");

        User user7 = new User();
        user7.setUsername("user7");
        user7.setPassword("password7");


        //generate the three player lists (with three in 1, four in 2, and six in 3)
        Player player1 = new Player();
        player1.setUsername("player1");
        player1.setCountry(country1);
        player1.setUser(user1);
        playerList1.add(player1);
        playerList2.add(player1);
        playerList3.add(player1);

        Player player2 = new Player();
        player2.setUsername("player2");
        player2.setCountry(country2);
        player2.setUser(user2);
        playerList1.add(player2);
        playerList2.add(player2);
        playerList3.add(player2);

        Player player3 = new Player();
        player3.setUsername("player3");
        player3.setCountry(country3);
        player3.setUser(user3);
        playerList1.add(player3);
        playerList2.add(player3);
        playerList3.add(player3);

        Player player4 = new Player();
        player4.setUsername("player4");
        player4.setCountry(country4);
        player4.setUser(user4);
        playerList2.add(player4);
        playerList3.add(player4);

        Player player5 = new Player();
        player5.setUsername("player5");
        player5.setCountry(country5);
        player5.setUser(user5);
        playerList3.add(player5);

        Player player6 = new Player();
        player6.setUsername("player6");
        player6.setCountry(country6);
        player6.setUser(user6);
        playerList3.add(player6);

        Player player7 = new Player();
        player7.setUsername("player7");
        player7.setCountry(country7);
        player7.setUser(user7);
        playerList3.add(player7);


        //set the servers
        server1.setPlayer(playerList1);
        server1.setServerSettings(ss1);

        server2.setPlayer(playerList2);
        server2.setServerSettings(ss2);

        server3.setPlayer(playerList3);
        server3.setServerSettings(ss3);

    }


    @Test
    public void testGame1(){
        Game game1 = new Game(server1);
    }

    @Test
    public void testGame2(){
        Game game2 = new Game(server2);
    }

    @Test
    public void testGame3(){
        Game game3 = new Game(server3);
    }

}
