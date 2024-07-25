package org.mude.rest;

import org.mude.service.i.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {
    @Autowired
    public AdminRestController(UserService userService) {
    }

    @GetMapping(value = "api/admin")
    public String admin(){
        return "ADMIN";
    }
}
