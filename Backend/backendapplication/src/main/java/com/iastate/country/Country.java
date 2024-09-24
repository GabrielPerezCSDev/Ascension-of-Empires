package com.iastate.country;

import com.iastate.player.Player;
import com.iastate.serversetting.ServerSettings;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;

import java.util.List;

/**
 *
 * @author Gabriel Perez
 *
 */

@Entity
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Name of the country", name="name", required=false, value="test country")
    private String name;
    @ApiModelProperty(notes = "Gold per turn of the country", name="gpt", required=false, value="100")
    private int gpt;
    @ApiModelProperty(notes = "Growth per turn of the country", name="grwpt", required=false, value="100")
    private int grwpt;

    @ApiModelProperty(notes = "Number of buildings in the country", name="buildings", required=false, value="100")
    private int buildings;

    @ApiModelProperty(notes = "Number of units in the country", name="units", required=false, value="100")
    private int units;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGpt() {
        return gpt;
    }

    public void setGpt(int gpt) {
        this.gpt = gpt;
    }

    public int getGrwpt() {
        return grwpt;
    }

    public void setGrwpt(int grwpt) {
        this.grwpt = grwpt;
    }

    public int getBuildings() {
        return buildings;
    }

    public void setBuildings(int buildings) {
        this.buildings = buildings;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

}
