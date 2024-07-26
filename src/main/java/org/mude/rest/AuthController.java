package org.mude.rest;


import lombok.extern.slf4j.Slf4j;
import org.mude.model.User;
import org.mude.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
            return ResponseEntity.ok(lUser);
        }
        return ResponseEntity.badRequest().build();
        }
}