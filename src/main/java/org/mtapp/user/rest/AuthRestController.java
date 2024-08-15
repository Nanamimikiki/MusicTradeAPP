package org.mtapp.user.rest;


import lombok.extern.slf4j.Slf4j;
import org.mtapp.user.model.User;
import org.mtapp.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/add/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            log.info("In registerUser - user {} registered successfully", user.getUsername());
            return ResponseEntity.ok(registeredUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User lUser = userService.loginUser(user);
        if (lUser != null) {
            log.info("In loginUser - user {} logged in successfully", user.getUsername());
            return ResponseEntity.ok(lUser);
        }
        log.info("Bad credentials!");
        return ResponseEntity.badRequest().build();
        }
}