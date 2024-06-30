package org.mude.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/a")
public class AdminRestController {
    @GetMapping(value = "/admin")
    public String admin(){return "ADMIN";}
}
