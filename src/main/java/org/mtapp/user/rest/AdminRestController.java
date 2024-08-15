package org.mtapp.user.rest;

import org.mtapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping(value = "api/admin")
@RestController
public class AdminRestController {
    @Autowired
    public AdminRestController(UserService userService) {
    }

    @Bean
    public String admin(){
        return "ADMIN";
    }
}
