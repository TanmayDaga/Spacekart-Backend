package net.in.spacekart.backend.controllers.apis.v1;


import net.in.spacekart.backend.payloads.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.in.spacekart.backend.services.entityServices.UserService;

import java.util.List;

@RestController
public class UserController {



    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<UserDto> getAll(){

        return userService.getAllUsers();

    }
}
