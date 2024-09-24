package com.iastate.session;

import com.iastate.player.Player;
import com.iastate.user.User;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
/**
 *
 * @author Gabriel Perez
 *
 */
public class UserDetails {

    public static Long userId;
    public static String username;
    public static User user;
    public static ArrayList<User> userList = new ArrayList<>();
    public UserDetails(User user) {
        UserDetails.user = user;
        UserDetails.userId = user.getId();
        UserDetails.username = user.getUsername();
        addUser(user);
    }

    public void addUser(User ud){
        UserDetails.userList.add(ud);
    }

    public HttpSession getSession(HttpSession session){
        return session;
    }
}
