package com.iastate.board;

import com.iastate.player.Player;
import com.iastate.tile.Tile;

import java.util.List;

public class LargeBoard extends Board {
    List<Player> playerList;
    public LargeBoard(List<Player> playerList){
        super(225);
        super.playerList = playerList;
        System.out.println();
        generateBoard();
    }

}
