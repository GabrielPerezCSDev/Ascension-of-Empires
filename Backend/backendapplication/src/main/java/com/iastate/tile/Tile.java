package com.iastate.tile;

import com.iastate.resources.Resources;
import com.iastate.resources.Resources.Type;
import com.iastate.player.Player;
/**
 *
 * @author Gabriel Perez
 *
 */
public class Tile {
    int position;
    Player owner;
    Resources resource;
    Type type;
    public Tile(Resources resource, int position){
        this.resource = resource;
        this.type = resource.getType();
        this.position = position;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public Player getOwner(){
        return owner;
    }

    public Resources getResource(){
        return resource;
    }

    public int getPosition(){
        return position;
    }

    // Getter method for the 'type' field
    public Type getType() {
        return this.type;
    }

}
