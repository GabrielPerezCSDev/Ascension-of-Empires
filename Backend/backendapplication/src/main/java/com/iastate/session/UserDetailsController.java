package com.iastate.session;

import com.iastate.user.User;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
/**
 *
 * @author Gabriel Perez
 *
 */
@RestController
@EnableAutoConfiguration
@Api(value = "UserDetailsController", description = "Fully deprecated class that was used for debugging and can be ignored (just checks the sessions user details)")
public class UserDetailsController {
    UserDetails Uud;
    @GetMapping("/UserDetails")
    public User getUserDetails() {
        if(UserDetails.userId == null){
            return null;
        }else{
            return UserDetails.user;
        }

    }

    @GetMapping("/UserDetails/CurrentUsers")
    public ArrayList<User> getCurrentUsers(){
        return UserDetails.userList;
    }


   // @GetMapping("/UserDetails/CurrentUsers")
    //public HttpSession getHTTPSession(){
      //  return Uud.getSession(HttpSession session);


}

