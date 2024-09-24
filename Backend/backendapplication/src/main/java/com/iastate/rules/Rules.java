package com.iastate.rules;

import com.iastate.board.Board;
import com.iastate.game.Game;
import com.iastate.player.Player;
import com.iastate.shop.Items;
import com.iastate.shop.ItemsRepo;
import com.iastate.tile.Tile;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
public class Rules {


    @Autowired
    ItemsRepo itemsRepo;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private int turnCounter = 0;
    //keep track of how many turns each player has made
    private int totalTurns = 0;
    int[] playerTurns;

    private boolean boardGrowSocket = false;

    //lets have a player make a turn
    public void playerTurn(HttpSession session, Game game){

        //if the turns are equal to the number of permissable turns then end the game
        if(totalTurns > game.getTurnLimit()){
            //end the game
            System.out.println();
            System.out.println("Game is over");

            //now we need to calculate a winner
            List<Player> winnerList = calcWinner(game);
            //calcWinner(game);
            //need to send a websocket message to the front end to display the winner

        }else {
            //have a player try and do their turn
            playerTurns = game.getPlayerTurnCounter();
            if (isPlayersTurn(session, game, turnCounter)) {
                System.out.println(session.getAttribute("userId") + " has made a turn");
                game.setNumTurns(game.getNumTurns() + 1);
                //increment the users turn
                playerTurns[turnCounter] = playerTurns[turnCounter] + 1;
                //incrment the overall counter
                turnCounter++;

                //reset the turn counter back to start after final player uses it
                if (turnCounter > game.getPlayerList().size() - 1) {
                    turnCounter = 0;
                    //process the end of the turn for all players
                    processPlayersTurn(playerTurns, game);
                    //new round of turns
                    System.out.println();
                    System.out.println();
                    System.out.println("New round of turns");
                    //print the board
                    game.getBoard().printPlayerBoard();
                }
            } else {
                System.out.println("Not users turn");
            }
        }
    }

    //create a method to keep track of what users turn it is
    public boolean isPlayersTurn(HttpSession session, Game game, int turnCounter){
        List<Player> pList = game.getPlayerList();
        Player currentPlayer = pList.get(turnCounter);
        Long currentUserId = currentPlayer.getUser().getId();

        return session.getAttribute("userId") == currentUserId;
    }

    public void processPlayersTurn(int[] playerTurns, Game game){
        System.out.println();
        System.out.println("turn is over time to process");

        //compare and add any players that need to be updated
        List<Player> updateList = new ArrayList<>();
        for(int i = 0; i < playerTurns.length; i++){
            if(playerTurns[i] - game.getPlayerList().get(i).getGrwpt() <= 0){
                System.out.println("need to update player: " + i);
                boardGrowSocket = true;
                List<Tile> tileList = generatePlayerBorderAdjList(game.getPlayerList().get(i), game);
                expandPlayerBorder(game.getPlayerList().get(i), game, tileList);
                playerTurns[i] = 0; //reset the counter

                //this is where they will be sent to update the owned player tiles (increase their border)



                updateList.add(game.getPlayerList().get(i));
            }
        }

        //now for loop has run send the players to the update map method (send to board)

        System.out.println();
        System.out.println();
        //need to update player gold for each turn
        for(Player p : game.getPlayerList()){
            updatePlayerGoldAfterTurn(p);
        }

        //need to update players gpt/grwpt after their turn
        for(Player p : game.getPlayerList()){
            updatePlayerGoldAfterTurn(p);
        }

        //update player values
        for(Player p : game.getPlayerList()){
            updatePlayerValues(p);
        }

        //add a web socket to notify the front end the map has changed
        if(boardGrowSocket){
            System.out.println("Sending websocket message");
            simpMessagingTemplate.convertAndSend("/topic/game-update", game.getBoard());
        }

        //simpMessagingTemplate.convertAndSend("/topic/player-update", game.getPlayerList());
        //set the web socket to null
        boardGrowSocket = false;
        //increase the total turns
        totalTurns++;
    }


    //logic for the player buying an item in the shop
    public void buyItem(Long itemId, HttpSession session, Game game) {

        System.out.println();
        //first need to check if it is the current players turn
        if (isPlayersTurn(session, game, turnCounter)) {
            Optional<Items> optionalItem = itemsRepo.findById(itemId);
            if (optionalItem.isPresent()) {
                Items item = optionalItem.get();

                Double cost = item.getItemCost();

                //check if current player can afford the item
                List<Player> pList = game.getPlayerList();
                Player currentPlayer = pList.get(turnCounter);
                //check their gold vs cost
                if(currentPlayer.getGold() < cost){
                    System.out.println("Insufficient funds");
                    return;
                }
                //update player if they can
                System.out.print(currentPlayer.getUsername() + ": is buying item: " + item.getItemName());
                currentPlayer.getItems().add(item);
                currentPlayer.setTotalItemsOwned(currentPlayer.getTotalItemsOwned()+1);
                updatePlayerGold(currentPlayer, cost);
                //also need to update their items

            } else {
                System.out.println("No such item");
            }
        }

    }


    public void updatePlayerGoldAfterTurn(Player player){
        player.setGold(player.getGold() + player.getGpt());
        System.out.println(player.getUsername() + " gold after turn: " + player.getGold());
    }

    public void updatePlayerGold(Player player, Double cost){
        player.setGold(player.getGold()-cost);
        System.out.println("Players gold after purchase: " + player.getGold());
    }

    public List<Player> calcWinner(Game game){
        //find which player has the most tiles at the end of the game
        //if there is a tie then the player with the most gold wins
        //if there is still a tie then player with the most items wins
        List<Player> candidateList = new ArrayList<>();

        //just a print to show players in the game and their stats
        System.out.println("PLAYER NUMBER: " + game.getPlayerList().size());
        for(Player p : game.getPlayerList()){
            System.out.println(p.getUsername() + " has " + p.getTotalItemsOwned() + " items");
            System.out.println(p.getUsername() + " has " + p.getGold() + " gold");
            System.out.println(p.getUsername() + " has " + p.getTiles().size() + " tiles");
        }

        //get the list of players
        List<Player> pList = game.getPlayerList();
        int maxTiles = 0;
        for(Player p : pList){
            if(p.getTotalItemsOwned() > maxTiles){
                maxTiles = p.getTotalItemsOwned();
                //new max found so clear the list
                candidateList.clear();
                candidateList.add(p);
            }

            //if equal add this player to the cadidate list for winner
            if(p.getTotalItemsOwned() == maxTiles){
                candidateList.add(p);
            }
        }

        //check if there is only one candidate (if so they have won)
        if(candidateList.size() == 1){
            return candidateList;
        }

        //now do the same process with the candidate list but for gold as a tiebreaker
        List<Player> candidateList2 = new ArrayList<>();
        double maxGold = 0;
        for(Player p : candidateList){
            if(p.getGold() > maxGold){
                maxGold = p.getGold();
                //new max found so clear the list
                candidateList2.clear();
                candidateList2.add(p);
            }

            //if equal add this player to the cadidate list for winner
            if(p.getGold() == maxGold){
                candidateList2.add(p);
            }
        }

        //check if there is only one candidate (if so they have won)
        if(candidateList2.size() == 1){
            return candidateList2;
        }

        //now for the final tiebreaker we will use items
        List<Player> candidateList3 = new ArrayList<>();
        int maxItems = 0;
        for(Player p : candidateList2){
            if(p.getTotalItemsOwned() > maxItems){
                maxItems = p.getTotalItemsOwned();
                //new max found so clear the list
                candidateList3.clear();
                candidateList3.add(p);
            }

            //if equal add this player to the cadidate list for winner
            if(p.getTotalItemsOwned() == maxItems){
                candidateList3.add(p);
            }
        }

        //return the final candidate list to the front end if a winner/winners are found

        return candidateList3;
    }

    //this will intially set the player values at the start of the game
    public void setInitialPlayerValues(List<Player> playerList){

        for(Player p : playerList){
            updatePlayerValues(p);
        }


    }

    //this will update the player values after a turn
    public void updatePlayerValues(Player p){
        System.out.println();
        System.out.println("Updating "+ p.getUsername() + " values");
        //update gpt and grwpt based on tiles owned
        double additionalGpt = 0;
        double additionalGrwpt = 1;
        for(Tile tile : p.getTiles()){
            additionalGpt += tile.getResource().getGptBonus();
            additionalGrwpt *= tile.getResource().getGrwptModifier();
        }

        //now update based on items owned
        for(Items item : p.getItems()){
            additionalGpt += item.getGptBonus();
            additionalGrwpt *= item.getGrwptModifier();
        }

        //now update the player values
        p.setGpt(p.getCountry().getGpt()+additionalGpt);
        p.setGrwpt((double) p.getCountry().getGrwpt() *additionalGrwpt);
        System.out.println(p.getUsername()+"'s GPT: " + p.getGpt());
        System.out.println(p.getUsername()+"'sGRWPT: " + p.getGrwpt());
    }

    public List<Tile> generatePlayerBorderAdjList(Player player, Game game){
        System.out.println();
        System.out.println();
        System.out.println("Generating adjacency list for player: " + player.getUsername());
        List<Tile> adjTiles = new ArrayList<>();

        //ignore all tiles on edges of board (set up booleans)
        int boardLength = (int) Math.sqrt(game.getBoard().getSize());
        int[] directions = {1,-1, boardLength, -boardLength};
        for(Tile tile : player.getTiles()){
            //System.out.println();
           // System.out.println("Inspecting tile: " + tile.getPosition());

            //check if it is in a corner (can't move anywhere)
            boolean topLeftCorner = tile.getPosition() == 1;
            boolean topRightCorner = tile.getPosition() == game.getBoard().getSize();
            boolean bottomLeftCorner = tile.getPosition() == game.getBoard().getSize() * (game.getBoard().getSize() - 1) + 1;
            boolean bottomRightCorner = tile.getPosition() == game.getBoard().getSize() * game.getBoard().getSize();
            //System.out.println("Top left corner test: "+1);
            //System.out.println("Top right corner test: "+game.getBoard().getSize());
            //System.out.println("Bottom left corner test: "+(game.getBoard().getSize() * (game.getBoard().getSize() - 1) + 1));
            //System.out.println("Bottom right corner test: "+(game.getBoard().getSize() * game.getBoard().getSize()));
            boolean isValid = !topLeftCorner && !topRightCorner && !bottomLeftCorner && !bottomRightCorner;
            if(isValid){
               // System.out.println("Valid tile: " + tile.getPosition());
                //Edge check
                boolean topEdge = tile.getPosition() <= game.getBoard().getSize();
                boolean bottomEdge = tile.getPosition() >= game.getBoard().getSize() * (game.getBoard().getSize() - 1);
                boolean leftEdge = tile.getPosition() % game.getBoard().getSize() == 1;
                boolean rightEdge = tile.getPosition() % game.getBoard().getSize() == 0;
                Board board = game.getBoard();

                //calc pos left right up down
                int posLeft = tile.getPosition() - 1;
                int posRight = tile.getPosition() + 1;
                int posUp = tile.getPosition() - board.getSize();
                int posDown = tile.getPosition() + board.getSize();
                if(topEdge){
                   // System.out.println("TOP EDGE");
                    //check left
                   // System.out.println("Tile "+board.getTile(posLeft).getPosition() + " is owned by: " + board.getTile(posLeft).getOwner());
                    if(board.getTile(posLeft).getOwner() == null){
                   //     System.out.println("Adding tile");
                        adjTiles.add(board.getTile(posLeft));
                    }

                    //check right
                  //  System.out.println("Tile "+board.getTile(posRight).getPosition() + " is owned by: " + board.getTile(posRight).getOwner());
                    if(board.getTile(posRight).getOwner() == null){
                   //     System.out.println("Adding tile");
                        adjTiles.add(board.getTile(posRight));
                    }
                    //check down
                    System.out.println("Tile "+board.getTile(posDown).getPosition() + " is owned by: " + board.getTile(posDown).getOwner());
                    if(board.getTile(posDown).getOwner() == null){
                   //     System.out.println("Adding tile");
                        adjTiles.add(board.getTile(posDown));
                    }
                    //check down
                } else if (bottomEdge) {
                   // System.out.println("BOTTOM EDGE");
                   // System.out.println("Tile "+board.getTile(posLeft).getPosition() + " is owned by: " + board.getTile(posLeft).getOwner());
                    if(board.getTile(posLeft).getOwner() == null){
                        adjTiles.add(board.getTile(posLeft));
                    }

                    //check right
                   // System.out.println("Tile "+board.getTile(posRight).getPosition() + " is owned by: " + board.getTile(posRight).getOwner());
                    if(board.getTile(posRight).getOwner() == null){
                        adjTiles.add(board.getTile(posRight));
                    }
                    //check down
                   // System.out.println("Tile "+board.getTile(posUp).getPosition() + " is owned by: " + board.getTile(posUp).getOwner());
                    if(board.getTile(posUp).getOwner() == null){
                        adjTiles.add(board.getTile(posUp));
                    }
                } else if (leftEdge) {
                    //System.out.println("LEFT EDGE");
                    //check right
                    //System.out.println("Tile "+board.getTile(posRight).getPosition() + " is owned by: " + board.getTile(posRight).getOwner());
                    if(board.getTile(posRight).getOwner() == null){
                        adjTiles.add(board.getTile(posRight));
                    }

                    //check up
                    //System.out.println("Tile "+board.getTile(posUp).getPosition() + " is owned by: " + board.getTile(posUp).getOwner());
                    if(board.getTile(posUp).getOwner() == null){
                        adjTiles.add(board.getTile(posUp));
                    }

                    //check down
                    //System.out.println("Tile "+board.getTile(posDown).getPosition() + " is owned by: " + board.getTile(posDown).getOwner());
                    if(board.getTile(posDown).getOwner() == null){
                        adjTiles.add(board.getTile(posDown));
                    }
                } else if (rightEdge) {
                    //System.out.println("Right EDGE");
                    //check left
                    //System.out.println("Tile "+board.getTile(posLeft).getPosition() + " is owned by: " + board.getTile(posLeft).getOwner());
                    if(board.getTile(posLeft).getOwner() == null){
                        adjTiles.add(board.getTile(posLeft));
                    }

                    //check up
                    //System.out.println("Tile "+board.getTile(posUp).getPosition() + " is owned by: " + board.getTile(posUp).getOwner());
                    if(board.getTile(posUp).getOwner() == null){
                        adjTiles.add(board.getTile(posUp));
                    }

                    //check down
                    //System.out.println("Tile "+board.getTile(posDown).getPosition() + " is owned by: " + board.getTile(posDown).getOwner());
                    if(board.getTile(posDown).getOwner() == null){
                        adjTiles.add(board.getTile(posDown));
                    }
                }else{
                    //System.out.println("NOT AN EDGE");
                    //check left
                    //System.out.println("Tile "+board.getTile(posLeft).getPosition() + " is owned by: " + board.getTile(posLeft).getOwner());
                    if(board.getTile(posLeft).getOwner() == null) {
                        adjTiles.add(board.getTile(posLeft));
                    }
                    //check right
                    //System.out.println("Tile "+board.getTile(posRight).getPosition() + " is owned by: " + board.getTile(posRight).getOwner());
                    if(board.getTile(posRight).getOwner() == null){
                        adjTiles.add(board.getTile(posRight));
                    }

                    //check up
                   // System.out.println("Tile "+board.getTile(posUp).getPosition() + " is owned by: " + board.getTile(posUp).getOwner());
                    if(board.getTile(posUp).getOwner() == null){
                        adjTiles.add(board.getTile(posUp));
                    }

                    //check down
                    //System.out.println("Tile "+board.getTile(posDown).getPosition() + " is owned by: " + board.getTile(posDown).getOwner());
                    if(board.getTile(posDown).getOwner() == null){
                        adjTiles.add(board.getTile(posDown));
                    }

                }

            }

        }

        //print the adj list by tile position (just for testing)
        System.out.println("Adjacency list for "+  player.getUsername() + ": ");
        for(Tile tile : adjTiles){
            System.out.print(tile.getPosition() + " ");
        }
        return adjTiles;
    }

    public void expandPlayerBorder(Player player, Game game, List<Tile> adjTiles){

        //select a random tile form the list of adjacent tiles
        int rand = (int) (Math.random() * adjTiles.size());
        Tile tile = adjTiles.get(rand);
        System.out.println("Giving Player Tile at pos: " + tile.getPosition());
        //set the owner of the tile to the player
        tile.setOwner(player);
        //add the tile to the players list of owned tiles
        player.getTiles().add(tile);
        //increase the players total tiles owned
        player.setTotalTilesOwned(player.getTotalTilesOwned()+1);

    }



}
