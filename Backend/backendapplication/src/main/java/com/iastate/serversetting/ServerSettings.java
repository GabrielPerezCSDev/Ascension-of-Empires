package com.iastate.serversetting;

import com.iastate.country.Country;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Entity
@Table(name="server_settings")
public class ServerSettings {

    @ApiModelProperty(notes = "ID of the server settings", name="id", required=true, value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Number of players allowed in server ", name="size", required=true, value="5")
    private int size;

    @ApiModelProperty(notes = "Name of the server", name="name", required=true, value="Server1")
    private String name;

    @ApiModelProperty(notes = "Turn limit of the server", name="turnLimit", required=true, value="100")
    private int turnLimit;

    @ApiModelProperty(notes = "List of countries in the server", name="countryList", required=true, value="{Country1, Country2, Country3, Country4}")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "server_settings_country",
            joinColumns = @JoinColumn(name = "server_settings_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countryList;

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurnLimit() {
        return turnLimit;
    }

    public void setTurnLimit(int turnLimit) {
        this.turnLimit = turnLimit;
    }
}
