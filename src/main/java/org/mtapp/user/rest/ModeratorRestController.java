package org.mtapp.user.rest;

import lombok.extern.slf4j.Slf4j;
import org.mtapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/moderator")
public class ModeratorRestController {
    @Autowired
    public ModeratorRestController(UserService userService) {
    }
    public String moderator(){
        return "MODER";
    }
}
