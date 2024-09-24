package com.iastate.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iastate.country.Country;
import com.iastate.serversetting.ServerSettings;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import com.iastate.player.Player;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Entity
@Table(name="server")
public class Server {
    @ApiModelProperty(notes = "ID of the server", name="id", required=true, value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(notes = "List of players in the server", name="player", required=true, value="{player1, player2, player3, player4}")
    @OneToMany(mappedBy = "server", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("server")
    private List<Player> player;

    @ApiModelProperty(notes = "Server settings for the server", name="serverSettings", required=true, value="ServerSettings1")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "server_settings_id", referencedColumnName = "id")
    ServerSettings serverSettings;


    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    public void setServerSettings(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }


}
