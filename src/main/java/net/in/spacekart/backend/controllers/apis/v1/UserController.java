package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.put.user.UserPutDto;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @ModelAttribute UserPutDto userPutDto, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        userService.updateUser(userPutDto, userAuthentication.getUsername());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/summary/{username}")
    public ResponseEntity<?> getUserSummary(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserSummaryByUsername(username),HttpStatus.FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        if(authentication!= null){
            UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
            userService.deleteUser(userAuthentication.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




}
