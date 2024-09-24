package com.iastate.board;

import com.iastate.player.Player;
import com.iastate.resources.Resources;
import com.iastate.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SmallBoard extends Board{

    List<Player> playerList;
    public SmallBoard(List<Player> playerList){
        super(100);
        super.playerList = playerList;
        System.out.println();
        generateBoard();
    }
}
