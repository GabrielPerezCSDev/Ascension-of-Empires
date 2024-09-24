package com.iastate.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iastate.player.Player;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Entity
@Table(name="items")
public class Items {

    @ApiModelProperty(notes = "ID of the item", name="id", required=true, value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(notes = "Name of the item", name="itemName", required=true, value="Mine")
    private String itemName;

    @ApiModelProperty(notes = "Cost of the item", name="itemCost", required=true, value="100.00")
    private Double itemCost;

    //@ApiModelProperty(notes = "Modifier of the item on player attributes", name="modifier", required=true, value="1.5")
    //private Double modifier;

    private double gptBonus;

    private double grwptModifier;

    //table relations
    //want many players --> to have many items
    @JsonIgnore
    @ManyToMany(mappedBy = "items")
    private List<Player> players = new ArrayList<>();;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    //public Double getModifier() {
      //  return modifier;
    //}

   // public void setModifier(Double modifier) {
       // this.modifier = modifier;
    //}


    public double getGptBonus() {
        return gptBonus;
    }

    public void setGptBonus(double gptBonus) {
        this.gptBonus = gptBonus;
    }

    public double getGrwptModifier() {
        return grwptModifier;
    }

    public void setGrwptModifier(double grwptModifier) {
        this.grwptModifier = grwptModifier;
    }
}
