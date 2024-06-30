package org.mude.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @GetMapping(value = "api/u")
    public String user(){return "USER";}
}
