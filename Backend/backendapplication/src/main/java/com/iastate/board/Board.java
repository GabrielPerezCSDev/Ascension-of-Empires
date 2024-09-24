package com.iastate.board;

import com.iastate.player.Player;
import com.iastate.resources.Resources;
import com.iastate.tile.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Board {

    @Autowired
    private SimpMessagingTemplate template;

    protected int SIZE;

    private Tile[][] grid;

    private Random rand = new Random();

     List<Player> playerList;

    public Board(int size) {
        this.SIZE = size;
        this.grid =  new Tile[(int) Math.sqrt(SIZE)][(int) Math.sqrt(SIZE)];
    }

    //generates a board (will call intialize board and setPlayers)
    public void generateBoard() {
        intializeBoard();
        setPlayers();
    }

    //this will intialize a board with no player ownership
    public void intializeBoard(){
        int resourcePick;
        int row;            //row of the grid
        int col;            //column of the grid
        for(int i = 0; i < Math.sqrt(SIZE); i++){
            for(int j = 0; j < Math.sqrt(SIZE); j++){
                //check if the tile has already been filled
                if(grid[i][j] == null){
                    //get the resource for this tile
                    //use probability to weight the thingy (create a method for it (resource class))
                    resourcePick = rand.nextInt(101);
                    Resources r= new Resources(resourcePick, SIZE);
                    ArrayList<Integer> rLocations = r.getResourceLocations(getPosition(i,j));           //now we have where to set the recource cluster

                    for(Integer l : rLocations){
                        Tile t = new Tile(r,l);         //set the tile resource and position
                        //set it on the grid
                        row = (int) ((l-1) / Math.sqrt(SIZE));
                        col = (int) ( (l-1) % Math.sqrt(SIZE));
                        grid[row][col] = t;
                    }
                }
                //continue
            }
        }
    }

    //this will set the players on the board to their respective tiles
    public void setPlayers(){
        //set the players in start positions
        //pattern for positions regardless of players [0,SIZE*SIZE,SIZE*SIZE-SIZE+1,
        int row;            //row of the grid
        int col;
        int[] start = playerStartLocations();

        int startPosition;
        Tile t;
        for(int i = 0; i < playerList.size(); i++){
            //set the player at the right position
            //get the tile at this position
            startPosition = start[i];
            //System.out.println("Player " + i+1+" will start at " + startPosition);
            row = (int) ((startPosition-1) / Math.sqrt(SIZE));
            col = (int) ( (startPosition-1) % Math.sqrt(SIZE));
            t = grid[row][col];
            //set the player in this tile
            t.setOwner(playerList.get(i));

            //increment the number of tiles this player owns
            playerList.get(i).setTotalTilesOwned(playerList.get(i).getTotalTilesOwned() + 1);
            //set the tile in the player
            playerList.get(i).getTiles().add(t);
            //playerList.get(i).getPlayerSquares().add(t);
            //generate the starting owned tiles for this player
            generateInitialPlayerSquares(startPosition, playerList.get(i));
            //make method to generate starting adjacency lists

        }
    }


    //this will get the absoluite position from the grid matrix [i,j]
    public int getPosition(int i, int j) {

        return (int) (i * Math.sqrt(SIZE) + j + 1);
    }

    public void generateInitialPlayerSquares(int position, Player player){
        int sideLength = (int) Math.sqrt(SIZE);
        ArrayList<Integer> adjList = new ArrayList<>();
        System.out.println("generateInitialPlayerSquares() called with position: "+position);
        //top left edge
        if(position == 1){
            adjList.add(position + 1);
            adjList.add(position + 2);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            adjList.add(position + sideLength+2);
            adjList.add(position + sideLength*2);
            adjList.add(position + sideLength*2+1);
            adjList.add(position + sideLength*2+2);
        }
        //top right edge
        else if (position == sideLength) {
            adjList.add(position - 1);
            adjList.add(position - 2);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength-1);
            adjList.add(position + sideLength-2);
            adjList.add(position + sideLength*2);
            adjList.add(position + sideLength*2-1);
            adjList.add(position + sideLength*2-2);
        }
        //check if bottom right edge
        else if (position == sideLength*sideLength) {
            adjList.add(position - 1);
            adjList.add(position - 2);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength-1);
            adjList.add(position - sideLength-2);
            adjList.add(position - sideLength*2);
            adjList.add(position - sideLength*2-1);
            adjList.add(position - sideLength*2-2);
        }
        //check if bottom left edge
        else if (position == sideLength*sideLength - sideLength + 1) {
            adjList.add(position + 1);
            adjList.add(position + 2);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position - sideLength+2);
            adjList.add(position - sideLength*2);
            adjList.add(position - sideLength*2+1);
            adjList.add(position - sideLength*2+2);
        }
        //check if top edge
        else if (position <= sideLength) {
            adjList.add(position + 1);
            adjList.add(position - 1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            adjList.add(position + sideLength-1);
            adjList.add(position + sideLength*2);
            adjList.add(position + sideLength*2+1);
            adjList.add(position + sideLength*2-1);
        }
        //check if bottom edge
        else if (position > sideLength*sideLength - sideLength) {
            adjList.add(position + 1);
            adjList.add(position - 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position - sideLength-1);
            adjList.add(position - sideLength*2);
            adjList.add(position - sideLength*2+1);
            adjList.add(position - sideLength*2-1);
        }
        //check if left edge
        else if (position % sideLength == 1) {
            adjList.add(position + 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);

            adjList.add(position - sideLength*2);
            adjList.add(position - sideLength*2+1);
            adjList.add(position + sideLength*2);
            adjList.add(position + sideLength*2+1);

        }
        //check if right edge
        else if (position % sideLength == 0) {
            adjList.add(position - 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength-1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength-1);

            adjList.add(position - sideLength*2);
            adjList.add(position - sideLength*2-1);
            adjList.add(position + sideLength*2);
            adjList.add(position + sideLength*2-1);

        }
        //regular edge
        else{
            adjList.add(position - 1);
            adjList.add(position + 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength-1);
            adjList.add(position - sideLength+1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            adjList.add(position + sideLength-1);


        }


        //intiialize the positions
        Tile t;
        int row;
        int col;
        for(Integer l : adjList){
            row = (int) ((l-1) / Math.sqrt(SIZE));
            col = (int) ( (l-1) % Math.sqrt(SIZE));
            t = grid[row][col];
            //set the player in this tile
            t.setOwner(player);
            //increment the players owned tiles
            player.setTotalTilesOwned(player.getTotalTilesOwned() + 1);
            //set the player to own these tiles
            player.getTiles().add(t);

        }
    }

    public int[] playerStartLocations(){
        int[] locations = new int[8];
        int sizeLength = (int) Math.sqrt(SIZE);
        locations[0] = 1;
        locations[1] = SIZE;
        locations[2] = SIZE -sizeLength + 1;
        locations[3] = (int) Math.sqrt(SIZE);
        locations[4] = SIZE / 2 + 1;
        locations[5] = SIZE - (int)Math.sqrt(SIZE)/2;
        locations[6] = (int) Math.sqrt(SIZE)/2 + 1;
        locations[7] = SIZE / 2 + sizeLength;
        for(int i = 0; i < locations.length; i++){
            System.out.print(locations[i] + " ");
        }
        return locations;
    }


    //for printing the board
    public Tile[][] getGrid(){
        return this.grid;
    }


    public void printBoard(){
        System.out.println("Resource Board: ");
        for (int i = 0; i < Math.sqrt(SIZE); i++) {
            for (int j = 0; j < Math.sqrt(SIZE); j++) {
                Tile t = grid[i][j];
                Resources r = t.getResource();
                System.out.printf("%-10s", r.getType());
            }
            System.out.println();
        }
    }


    public void printPlayerBoard() {
        System.out.println("Ownership Board: ");
        for (int i = 0; i < Math.sqrt(SIZE); i++) {
            for (int j = 0; j < Math.sqrt(SIZE); j++) {
                Tile t = grid[i][j];
                if(t.getOwner() != null){
                    System.out.printf("%-10d", t.getOwner().getUser().getId());
                }else{
                    System.out.printf("%-10d", -1);
                }

            }
            System.out.println();
        }
    }



    public void expandPlayersBorder(List<Player> updatePlayers){

    };

    public int getSize(){
        return (int) Math.sqrt(SIZE);
    }


    public Tile getTile(int position) {
        int col = (int) ((position -1) % Math.sqrt(SIZE));
        int row = (int) ((position -1) / Math.sqrt(SIZE));
        return grid[row][col];
    }

}
