package com.iastate.backendapplication.board;

import com.iastate.board.LargeBoard;
import com.iastate.board.MediumBoard;
import com.iastate.board.SmallBoard;
import com.iastate.player.Player;
import org.junit.jupiter.api.Test;

import java.util.*;

public class boardTests {

    @Test
    void testSmallBoard() {
        List<Player> players = new ArrayList<>();
        // Add players to the list
        Player player1 = new Player();
        player1.setUsername("player1");
        players.add(player1);
        Player player2 = new Player();
        player2.setUsername("player2");
        players.add(player2);
        Player player3 = new Player();
        player3.setUsername("player3");
        players.add(player3);
        SmallBoard smallBoard = new SmallBoard(players);
        // Perform assertions on the smallBoard instance
    }

    @Test
    void testMediumBoard() {
        List<Player> players = new ArrayList<>();
        // Add players to the list
        Player player1 = new Player();
        player1.setUsername("player1");
        players.add(player1);
        Player player2 = new Player();
        player2.setUsername("player2");
        players.add(player2);
        Player player3 = new Player();
        player3.setUsername("player3");
        players.add(player3);
        Player player4 = new Player();
        player4.setUsername("player4");
        players.add(player4);

        MediumBoard mediumBoard = new MediumBoard(players);
        // Perform assertions on the mediumBoard instance
    }

    @Test
    void testLargeBoard() {
        List<Player> players = new ArrayList<>();
        // Add players to the list
        Player player1 = new Player();
        player1.setUsername("player1");
        players.add(player1);
        Player player2 = new Player();
        player2.setUsername("player2");
        players.add(player2);
        Player player3 = new Player();
        player3.setUsername("player3");
        players.add(player3);
        Player player4 = new Player();
        player4.setUsername("player4");
        players.add(player4);
        Player player5 = new Player();
        player5.setUsername("player5");
        players.add(player5);
        Player player6 = new Player();
        player6.setUsername("player6");
        players.add(player6);

        LargeBoard largeBoard = new LargeBoard(players);
        // Perform assertions on the largeBoard instance
    }

}
