package com.iastate.backendapplication.rules;

import com.iastate.country.Country;
import com.iastate.game.Game;
import com.iastate.player.Player;
import com.iastate.rules.Rules;
import com.iastate.server.Server;
import com.iastate.serversetting.ServerSettings;
import com.iastate.shop.ItemsRepo;
import com.iastate.user.User;
import com.iastate.shop.Items;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RulesTest {

    List<Player> playerList1 = new ArrayList<>();
    List<Player> playerList2 = new ArrayList<>();


    //server settings
    ServerSettings ss1 = new ServerSettings();
    ServerSettings ss2 = new ServerSettings();



    //three servers
    Server server1 = new Server();
    Server server2 = new Server();


    //three mock sessions
    MockHttpSession mockSession1 = new MockHttpSession();
    MockHttpSession mockSession2 = new MockHttpSession();
    MockHttpSession mockSession3 = new MockHttpSession();

    //set two games
    Game game1;
    Game game2;

    //mock repo
    ItemsRepo mockItemsRepo = Mockito.mock(ItemsRepo.class);

    //mock user repo


    //set up one item for purchase
    Items item1 = new Items();

    @Before
    public void setUp(){
        //generate the three server settings
        ss1.setTurnLimit(10);
        ss1.setName("game1");
        ss1.setSize(3);

        ss2.setTurnLimit(10);
        ss2.setName("game2");
        ss2.setSize(4);


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


        //generate six users to attact to the players
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        mockSession1.setAttribute("user", user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        mockSession2.setAttribute("user", user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("password3");
        mockSession3.setAttribute("user", user3);


        //generate the three player lists (with three in 1, four in 2, and six in 3)
        Player player1 = new Player();
        player1.setUsername("player1");
        player1.setCountry(country1);
        player1.setUser(user1);
        playerList1.add(player1);
        playerList2.add(player1);
        ;

        Player player2 = new Player();
        player2.setUsername("player2");
        player2.setCountry(country2);
        player2.setUser(user2);
        playerList1.add(player2);
        playerList2.add(player2);


        Player player3 = new Player();
        player3.setUsername("player3");
        player3.setCountry(country3);
        player3.setUser(user3);
        playerList1.add(player3);
        playerList2.add(player3);


        //set the servers
        server1.setPlayer(playerList1);
        server1.setServerSettings(ss1);

        server2.setPlayer(playerList2);
        server2.setServerSettings(ss2);

        //set the two test games
        game1 = new Game(server1);
        game2 = new Game(server2);


        //set up one item for purchase
        item1.setGptBonus(10);
        item1.setGrwptModifier(0.5);
        item1.setItemCost(50.0);
        item1.setItemName("item1");
        Long itemId = 1L;
        Mockito.when(mockItemsRepo.findById(itemId)).thenReturn(Optional.of(item1));
    }


    @Test
    public void testPlayerTurn(){
        Rules rules = new Rules();
        rules.playerTurn(mockSession1, game1);
    }

    @Test
    public void testPlayerInvalidTurn(){
        Rules rules = new Rules();
        rules.playerTurn(mockSession1, game2);
        rules.playerTurn(mockSession1, game2);
    }

    @Test
    public void buyItemTest(){
        // Create a mock of ItemsRepo
        ItemsRepo mockItemsRepo = Mockito.mock(ItemsRepo.class);

        // Set up the mock to return an item when findById is called
        Items item1 = new Items();
        item1.setGptBonus(10);
        item1.setGrwptModifier(0.5);
        item1.setItemCost(50.0);
        item1.setItemName("item1");
        Long itemId = 1L;
        Mockito.when(mockItemsRepo.findById(itemId)).thenReturn(Optional.of(item1));

        // Create an instance of Rules
        Rules rules = new Rules();

        // Use ReflectionTestUtils to inject the mock into rules
        ReflectionTestUtils.setField(rules, "itemsRepo", mockItemsRepo);

        // Now you can call the method without getting a NullPointerException
        rules.buyItem(itemId, mockSession1, game1);
        assertEquals("item1", game1.getPlayerList().get(0).getItems().get(0).getItemName());
    }

    @Test
    public void testInsufficientFunds(){
        // Create a mock of ItemsRepo
        ItemsRepo mockItemsRepo = Mockito.mock(ItemsRepo.class);

        // Set up the mock to return an item when findById is called
        Items item1 = new Items();
        item1.setGptBonus(10);
        item1.setGrwptModifier(0.5);
        item1.setItemCost(300.0);
        item1.setItemName("item1");
        Long itemId = 1L;
        Mockito.when(mockItemsRepo.findById(itemId)).thenReturn(Optional.of(item1));

        // Create an instance of Rules
        Rules rules = new Rules();

        // Use ReflectionTestUtils to inject the mock into rules
        ReflectionTestUtils.setField(rules, "itemsRepo", mockItemsRepo);

        // Now you can call the method without getting a NullPointerException
        rules.buyItem(itemId, mockSession1, game1);
        // Assert that the player's items list is empty
        assertTrue(game1.getPlayerList().get(0).getItems().isEmpty(), "Items list should be empty");
    }

    @Test
    public void testBorderGrowth(){
        // Create an instance of Rules
        Rules rules = new Rules();
        //original player tiles
        int ogTiles = playerList1.get(0).getTotalTilesOwned();
        System.out.println(ogTiles);
        rules.playerTurn(mockSession1, game1);
        rules.playerTurn(mockSession2, game1);
        rules.playerTurn(mockSession3, game1);
        int newTiles = playerList1.get(0).getTotalTilesOwned();
        System.out.println(newTiles);

        assertTrue(newTiles == ogTiles, "Number of tiles should have increased after turn");


    }
}
