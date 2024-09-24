package com.iastate.user;

import com.iastate.player.PlayerRepo;
import com.iastate.session.UserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iastate.player.Player;

import java.util.List;
/**
 *
 * @author Gabriel Perez
 *
 */
@Api(value = "UserController", description = "REST APIs related to User Entity!!!!")
@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    //needed for CustomUserService constructor for JWT
    private UserRepo ur;

    @Autowired
    private PlayerRepo pr;

    //CREATE
    //register a new user
    @ApiOperation(value = "Create a new User in the System", response = Iterable.class, tags = "createUser")
    @PostMapping("users/register")
    public void register(@RequestBody User user) {
        us.register(user);
    }
    //READ
    @ApiOperation(value = "Get all users in the system ", response = Iterable.class, tags = "getAllUsers")
    @GetMapping("users")
    public List<User> users() {
        return us.getAllUsers();
    }
    //UPDATE
    @ApiOperation(value = "Update a User in the System by players current Session", response = Iterable.class, tags = "updateUser")
    @PostMapping("/users/update")
    public void updateUser(@RequestBody User user) {
        us.updateUser(user);
    }
    //DELETE
    @ApiOperation(value = "Delete a User by ID", response = Iterable.class, tags = "deleteUserByID")
    @PostMapping("/users/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        us.deleteUserByUserId(userId);
    }

    //EXTRA FUNCTIONALITY

    //try logging in as a user
    @ApiOperation(value = "Try logging in as a user", response = Iterable.class, tags = "login")
    @PutMapping("users/login/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password, HttpSession session) {
        System.out.println("TRYING LOGIN");
        if(us.isValidLogin(username, password)){
            System.out.println("PASSED LOGIN");
            session.setAttribute("userId", ur.findByUsername(username).getId());
            System.out.println("Session var: " + session.getAttribute("userId").toString());
            return "redirect:/securedPage";

        }else{
            return "redirect:/login";
        }

    }

    @ApiOperation(value = "Logout the User by players current session" , response = Iterable.class, tags = "logout")
    @PutMapping ("users/logout")
    public String logout(HttpSession session){
        us.logout(session);
        session.invalidate();
        return "redirect:/login";
    }

    @ApiOperation(value = "Get the current session", response = Iterable.class, tags = "getSession")
    @GetMapping ("/users/session")
    public HttpSession getSession(HttpSession session){
        return us.getSession(session);
    }

}
