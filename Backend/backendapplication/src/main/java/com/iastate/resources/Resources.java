package com.iastate.resources;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Gabriel Perez
 *
 */
public class Resources {

    private double gptBonus;

    private double grwptModifier;
    private Type type;
    int sideLength;
    public Resources(int r, int sideLengths){
        //70 % chance of standard
        if(r <= 70){
            this.type = Type.STANDARD;
            this.grwptModifier = 1;
        } else if (r < 90) { //10% iron
            this.type = Type.IRON;
            this.gptBonus = 1;
            this.grwptModifier = 0.99;
        } else{ //20% water
            this.type = Type.WATER;
            this.gptBonus = 2;
            this.grwptModifier = 0.98;
        }
        this.sideLength = (int) Math.sqrt(sideLengths);
    }
    public enum Type{
        STANDARD,
        IRON,
        WATER
    }

    //this will generate a list of positions to fill based on the resource
    public ArrayList<Integer> getResourceLocations(int position){
        //create an ArrayList of positions to fill
        ArrayList<Integer> positions = new ArrayList<>();

        //for holding the adjacecny list
        ArrayList<Integer> adjList = new ArrayList<>();
        int size;
        Random rand = new Random();    //used for randomly generating clusters
        int index;                     //index to grab from the adjacecny list
        int pos;                       // position to fill
        //for standard just return the position
        if(type == Type.STANDARD){
            positions.add(position);
            return positions;
        }else if(type == Type.IRON){
            //cluster size of 2 to 4
            positions.add(position);               //initial position is added
            //System.out.println("POSITION TO BE IN ADJ LIST: " + position);
            //System.out.println("sizelength: " + sideLength);
            adjList = adjList(position);           //generate the adjacency list
           // System.out.println("ADJACENCY LIST: " +adjList.toString());
            size = rand.nextInt(3) + 1;     //1-3 adjacent pos besides position will be filled with this resource
            for(int i = 0; i < size; i++){
                index = rand.nextInt(adjList.size());   //pick a random index for adjList
                positions.add(adjList.get(index));      //add this selected adjacent value to the positions list
            }
            return positions;
        }else if (type == Type.WATER){
            //cluster size of 4 to 6
            positions.add(position);               //initial positions is added
            adjList = adjList(position);           //generate the adjacency list
            size = rand.nextInt(3) + 1;     //1-3 adjacent pos besides position will be filled with this resource
            for(int i = 0; i < size; i++){
                index = rand.nextInt(adjList.size());   //pick a random index for adjList
                positions.add(adjList.get(index));      //add this selected adjacent value to the positions list
            }
            return positions;
        }
        return positions;
    }


    //helper method for getting the adjacecnt squares around the position sent in
    public ArrayList<Integer> adjList(int position){

        ArrayList<Integer> adjList = new ArrayList<>();
        //check if top left edge (must be equal to zero)
        if(position == 1){
            adjList.add(position + 1);
            adjList.add(position + 2);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            adjList.add(position + sideLength+2);
            return adjList;
        }
        //top right edge
        else if (position == sideLength) {
            adjList.add(position - 1);
            adjList.add(position - 2);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength-1);
            adjList.add(position + sideLength-2);
            return adjList;
        }
        //check if bottom right edge
        else if (position == sideLength*sideLength) {
            adjList.add(position - 1);
            adjList.add(position - 2);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength-1);
            adjList.add(position - sideLength-2);
            return adjList;
        }
        //check if bottom left edge
        else if (position == sideLength*sideLength - sideLength + 1) {
            adjList.add(position + 1);
            adjList.add(position + 2);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position - sideLength+2);
            return adjList;
        }
        //check if top edge
        else if (position <= sideLength) {
            adjList.add(position + 1);
            adjList.add(position - 1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            adjList.add(position + sideLength-1);
            return adjList;
        }
        //check if bottom edge
        else if (position > sideLength*sideLength - sideLength) {
            adjList.add(position + 1);
            adjList.add(position - 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position - sideLength-1);
            return adjList;
        }
        //check if left edge
        else if (position % sideLength == 1) {
            adjList.add(position + 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength+1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength+1);
            return adjList;
        }
        //check if right edge
        else if (position % sideLength == 0) {
            adjList.add(position - 1);
            adjList.add(position - sideLength);
            adjList.add(position - sideLength-1);
            adjList.add(position + sideLength);
            adjList.add(position + sideLength-1);
            return adjList;
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
            return adjList;
        }
    }

    public Type getType(){
        return type;
    }

    public double getGptBonus() {
        return gptBonus;
    }

    public double getGrwptModifier() {
        return grwptModifier;
    }
}
