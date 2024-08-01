package org.mude.rest;

import org.mude.model.User;
import org.mude.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/users")
public class UserRestController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<String> updateEmail(@PathVariable UUID userId, @RequestBody String newEmail) {
        userService.updateUserEmail(userId, newEmail);
        return ResponseEntity.ok("Email updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/registered")
    public ResponseEntity<List<User>> getUsersRegisteredBetween(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return ResponseEntity.ok(userService.findUsersRegisteredBetween(start, end));
    }
}
