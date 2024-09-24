package com.iastate.user;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import com.iastate.player.Player;
/**
 *
 * @author Gabriel Perez
 *
 */
@Entity
@Table(name="users")
public class User {
    @ApiModelProperty(notes = "ID of the user", name="id", required=true, value="1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Username of the user", name="username", required=true, value="GPerez55")
    private String username;

    @ApiModelProperty(notes = "Email of the user", name="email", required=true, value="Gperez@Gmail.com")
    private String email;

    @ApiModelProperty(notes = "User statistics of the user", name="userStatistics", required=true, value="Wins: 1, Losses: 0")
    private String userStatistics;

    @ApiModelProperty(notes = "Password of the user", name="password", required=true, value="password")
    private String password;

    //getters and setters
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserStatistics() {
        return userStatistics;
    }

    public void setUserStatistics(String userStatistics) {
        this.userStatistics = userStatistics;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   // public Player getPlayer() {
    //    return player;
   // }

    //public void setPlayer(Player player) {
     //   this.player = player;
   // }


}

