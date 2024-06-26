package net.in.spacekart.backend.controllers;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.payloads.LoginUserDto;
import net.in.spacekart.backend.payloads.UserDto;
import net.in.spacekart.backend.services.JwtService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.io.IOException;

@RestController
public class AuthController {


    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user){
        userService.saveUser(user, Role.ROLE_USER);
        return  ResponseEntity.status(HttpStatus.OK).body("User Registered Successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> AuthenticateAndGetToken(@RequestBody LoginUserDto loginUserDto, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {


        String username = userService.getUserByEmailIdOrPhoneNumber(loginUserDto.getEmailIdOrPhoneNumber()).getUsername();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginUserDto.getPassword()));
        System.out.println(authentication.getPrincipal());

        if(authentication.isAuthenticated()){
           Cookie cookie = new Cookie("auth",String.format("Bearer%s",jwtService.GenerateToken(username)));
            response.addCookie(cookie);

           return new ResponseEntity<String>("Authentication Successful", HttpStatus.FOUND);


        } else {
           throw  new AuthenticationException("User Does Not Exist");
        }

    }


}
