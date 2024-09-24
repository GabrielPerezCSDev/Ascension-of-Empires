package com.iastate.game;

import com.iastate.board.LargeBoard;
import com.iastate.board.MediumBoard;
import com.iastate.board.SmallBoard;
import com.iastate.player.Player;
import com.iastate.rules.Rules;
import com.iastate.server.Server;
import com.iastate.serversetting.ServerSettings;
import com.iastate.board.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;
/**
 *
 * @author Gabriel Perez
 *
 */
public class Game {

    @ApiModelProperty(notes = "List of players in the game", name="playerList", required=true, value="{player1, player2, player3, player4}")
    List<Player> playerList;

    @ApiModelProperty(notes = "Turn counter for the game", name="playerTurnCounter", required=true, value="[1,2,3,4]")
    int[] playerTurnCounter;
    @ApiModelProperty(notes = "ID of the game", name="gameId", required=true, value="1")
    Long gameId;
    @ApiModelProperty(notes = "Server settings for the game", name="ss", required=true, value="ServerSettings")
    ServerSettings ss;

    @ApiModelProperty(notes = "Board for the game", name="board", required=true, value="Board")
    private Board board;

    private Rules rules = new Rules();
    private int turnLimit;

    private int numTurns;
    //default constructor
    public Game(){

    }
    //constructor for Game
    //initialize and create generate board for the game
    public Game(Server s){
        this.playerList = s.getPlayer();
        this.gameId = s.getId();
        this.ss = s.getServerSettings();
        this.playerTurnCounter = new int[playerList.size()];
        this.turnLimit = ss.getTurnLimit();
        this.numTurns = 0;
        //get the board type
        int numPlayers = playerList.size();
        if(numPlayers < 4){
            System.out.println("Creating a sdmall board");
            this.board = new SmallBoard(playerList);
        } else if (numPlayers < 6) {
            this.board = new MediumBoard(playerList);
        }else{
            this.board = new LargeBoard(playerList);
        }

        //generate initial gpt
        initialTurnMods(this.playerList);
        //set and generate initial player values
        rules.setInitialPlayerValues(playerList);

        System.out.println();
        board.printBoard();
        System.out.println();
        board.printPlayerBoard();
    }


    //use the two above to create a game board --> inject information into the constructor

    //generate initial player gpt
    public void initialTurnMods(List<Player> playerList){
        for(Player player : playerList){
            player.setGpt(player.getCountry().getGpt());
            player.setGrwpt(player.getCountry().getGrwpt());
        }
    }

    //generate getters and setter


    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public ServerSettings getSs() {
        return ss;
    }

    public void setSs(ServerSettings ss) {
        this.ss = ss;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int[] getPlayerTurnCounter() {
        return playerTurnCounter;
    }

    public void setPlayerTurnCounter(int[] playerTurnCounter) {
        this.playerTurnCounter = playerTurnCounter;
    }

    public int getTurnLimit() {
        return turnLimit;
    }

    public void setTurnLimit(int turnLimit) {
        this.turnLimit = turnLimit;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

}
