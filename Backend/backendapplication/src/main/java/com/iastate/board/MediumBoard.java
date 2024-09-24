package com.iastate.board;

import com.iastate.player.Player;
import com.iastate.tile.Tile;

import java.util.List;

public class MediumBoard extends Board{

    List<Player> playerList;
    public MediumBoard(List<Player> playerList){
        super(144);
        super.playerList = playerList;
        System.out.println();
        generateBoard();
    }
}
