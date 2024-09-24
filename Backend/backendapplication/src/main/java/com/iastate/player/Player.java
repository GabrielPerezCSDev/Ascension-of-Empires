package com.iastate.player;


import com.fasterxml.jackson.annotation.*;
import com.iastate.country.Country;
import com.iastate.server.Server;
import com.iastate.tile.Tile;
import com.iastate.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import com.iastate.shop.Items;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */

@Entity
@Table(name="player")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = { "server", "user", "tiles" })
@JsonIdentityReference(alwaysAsId = false)
public class Player {
    @ApiModelProperty(notes = "ID of the player", name="id", required=true, value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Username of the player", name="username", required=true, value="player1")
    private String username;

    @ApiModelProperty(notes = "Gold of the player", name="gold", required=true, value="100.00")
    private Double gold = 100.00;

    private double gpt = 0.0;

    private double grwpt = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;


    @ApiModelProperty(notes = "Server/servers player is in", name="server", required=true, value="{Server1, Server2, Server3}")
    @ManyToOne
    //ignore the server id
    @JoinColumn(name = "server_id")
    @JsonIgnoreProperties({"players", "hibernateLazyInitializer"})
    //@JsonIgnoreProperties("server")
    private Server server;


    @ApiModelProperty(notes = "User of the player", name="user", required=true, value="User1")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "player_item",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Items> items = new ArrayList<>();



    private int totalTilesOwned = 0;

    private int totalItemsOwned = 0;

    @Transient
    private List<Tile> tiles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Double getGold() {
        return gold;
    }

    public void setGold(Double gold) {
        this.gold = gold;
    }

    public double getGpt() {
        return gpt;
    }

    public void setGpt(double gpt) {
        this.gpt = gpt;
    }

    public double getGrwpt() {
        return grwpt;
    }

    public void setGrwpt(double grwpt) {
        this.grwpt = grwpt;
    }

    public int getTotalTilesOwned() {
        return totalTilesOwned;
    }

    public void setTotalTilesOwned(int totalTilesOwned) {
        this.totalTilesOwned = totalTilesOwned;
    }

    public int getTotalItemsOwned() {
        return totalItemsOwned;
    }

    public void setTotalItemsOwned(int totalItemsOwned) {
        this.totalItemsOwned = totalItemsOwned;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

}
