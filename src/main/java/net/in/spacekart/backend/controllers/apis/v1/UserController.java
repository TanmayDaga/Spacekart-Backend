package net.in.spacekart.backend.controllers.apis.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import net.in.spacekart.backend.services.entityServices.UserService;

@RestController
public class UserController {



    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
}
