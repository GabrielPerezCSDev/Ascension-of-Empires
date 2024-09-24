package com.iastate.session;

import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Gabriel Perez
 *
 */
@RestController
@EnableAutoConfiguration
@Api(value = "SessionController", description = "For debugging and can be ignored (just checks and manipulates the users session variable)")
public class SessionController {

    @GetMapping("/userDetails/inspectSession")
    @ResponseBody
    public Map<String, Object> inspectSession(HttpSession session) {
        Map<String, Object> sessionDetails = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            sessionDetails.put(attributeName, session.getAttribute(attributeName));
        }

        return sessionDetails;
    }

    @PutMapping("/userDetails/changeSession/{userId}")
    public void updateSession(HttpSession session, @PathVariable Long userId){
        session.setAttribute("userId", userId);
    }
}
