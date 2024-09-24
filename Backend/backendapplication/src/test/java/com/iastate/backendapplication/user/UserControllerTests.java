package com.iastate.backendapplication.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iastate.player.PlayerRepo;
import com.iastate.user.User;
import com.iastate.user.UserController;
import com.iastate.user.UserRepo;
import com.iastate.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepo userRepository;

    @MockBean
    private PlayerRepo playerRepo;

    private User user;
    @BeforeEach
    public void setup(){
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    @Test
    public void testRegister() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        verify(userService, times(1)).register(Mockito.any(User.class));
    }

    /*@Test
    public void testLogin() throws Exception {
        String username = "testUser";
        String password = "testPassword";

        // Mock the behavior of the userService
        when(userService.isValidLogin(username, password)).thenReturn(true);

        // Perform the request and check the result
        mockMvc.perform(put("/users/login/{username}/{password}", "testUser", "testPassword"))

                .andExpect(status().isOk());

        // Verify that the isValidLogin method of the userService was called with the correct parameters
        verify(userService, times(1)).isValidLogin(username, password);
    }*/

}
