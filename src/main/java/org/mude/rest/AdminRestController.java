package org.mude.rest;

import lombok.extern.slf4j.Slf4j;
import org.mude.service.i.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminRestController {
    @Autowired
    public AdminRestController(UserService userService) {
    }
    public String admin(){
        return "ADMIN";
    }
}
