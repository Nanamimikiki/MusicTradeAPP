package org.mtapp.user.rest;

import lombok.extern.slf4j.Slf4j;
import org.mtapp.user.dto.UserDto;
import org.mtapp.user.model.User;
import org.mtapp.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/add/user")
    public ResponseEntity<UserDto> addUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            log.info("In registerUser - user {} registered successfully", user.getUsername());
            UserDto userDto = userService.toUserDto(registeredUser);
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody User credentials) {
        if (credentials.getUsername() == null || credentials.getPassword() == null) {
            log.info("Bad request - username or password not provided");
            return ResponseEntity.badRequest().body(null);
        }

        User lUser = userService.loginUser(credentials.getUsername(), credentials.getPassword());
        if (lUser != null) {
            log.info("In loginUser - user {} logged in successfully", credentials.getUsername());
            UserDto userDto = userService.toUserDto(lUser);
            return ResponseEntity.ok(userDto);
        }

        log.info("Bad credentials for user {}", credentials.getUsername());
        return ResponseEntity.badRequest().body(null);
    }

}
