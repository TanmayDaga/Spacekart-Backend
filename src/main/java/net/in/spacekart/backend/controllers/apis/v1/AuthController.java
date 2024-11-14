package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.database.enums.Role;
import net.in.spacekart.backend.exceptions.SpacekartBaseException;
import net.in.spacekart.backend.payloads.post.auth.ResendOtpDto;
import net.in.spacekart.backend.payloads.post.auth.SendOtpDto;
import net.in.spacekart.backend.payloads.post.auth.VerifyOtpDto;
import net.in.spacekart.backend.payloads.post.user.LoginUserDto;
import net.in.spacekart.backend.payloads.post.user.UserCreateDto;
import net.in.spacekart.backend.services.JwtService;
import net.in.spacekart.backend.services.OtpService;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {


    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UtilsService utilsService;
    private final OtpService otpService;


    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, Tika tika, ModelMapper modelMapper, UtilsService utilsService, OtpService otpService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

        this.utilsService = utilsService;
        this.otpService = otpService;
    }


    @PostMapping(value = "v1/logout")
    public ResponseEntity<?> logoutMe(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) {



        // Clear auth cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains("auth")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        // Clear security context
        SecurityContextHolder.clearContext();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "v1/signup")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserCreateDto userCreateDto, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() != null) {
            return new ResponseEntity<>("Please logout to signin", HttpStatus.UNAUTHORIZED);
        }

        try {
            userCreateDto.setPhoneNumber(jwtService.extractPayload(userCreateDto.getPhoneNumber()).substring(3));
            userCreateDto.setEmailId(jwtService.extractPayload(userCreateDto.getEmailId()));
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid phone number and email Address", HttpStatus.CONFLICT);
        }
        userService.insertUser(userCreateDto, Role.ROLE_USER);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(value = "v1/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpDto otpDto) throws SpacekartBaseException {

        String orderId = otpService.sendOtp(otpDto.getEmailIdOrPhoneNumber(), utilsService.checkEmailIdOrPhoneNumber(otpDto.getEmailIdOrPhoneNumber()));
        if (orderId != null) {
            return new ResponseEntity<>(orderId, HttpStatus.OK);
        }

        return new ResponseEntity<>("Request body invalid", HttpStatus.BAD_REQUEST);

    }


    @PostMapping("v1/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) throws SpacekartBaseException {

        boolean isValid = otpService.verifyOtp(verifyOtpDto.getOrderId(), verifyOtpDto.getOtp(), utilsService.checkEmailIdOrPhoneNumber(verifyOtpDto.getEmailIdOrPhoneNumber()), verifyOtpDto.getEmailIdOrPhoneNumber());
        if (isValid) {
            return new ResponseEntity<>(jwtService.GenerateToken(verifyOtpDto.getEmailIdOrPhoneNumber()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("v1/resendOTP")
    public ResponseEntity<?> resendOTP(@RequestBody ResendOtpDto resendOtpDto) throws SpacekartBaseException {
        String orderId = otpService.resendOTP(resendOtpDto.getOrderId());
        if (orderId != null) {
            return new ResponseEntity<>(new ResendOtpDto(orderId), HttpStatus.OK);
        }

        return new ResponseEntity<>("Request body invalid", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("v1/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@Valid @ModelAttribute LoginUserDto loginUserDto, HttpServletRequest request, HttpServletResponse response, Authentication authentication1) throws AuthenticationException, IOException {

        if (authentication1 != null && authentication1.getPrincipal() != null) {
            return new ResponseEntity<>("Please logout to Login", HttpStatus.UNAUTHORIZED);
        }


        String username = userService.getUserByEmailIdOrPhoneNumber(loginUserDto.getEmailIdOrPhoneNumber());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginUserDto.getPassword()));


        if (authentication.isAuthenticated()) {
            Cookie cookie = new Cookie("auth", String.format("Bearer%s", jwtService.GenerateToken(username)));
            response.addCookie(cookie);

            return new ResponseEntity<>(username, HttpStatus.OK);


        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("v1/username")
    public ResponseEntity<?> getUserName(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        return ResponseEntity.ok(userAuthentication.getUsername());
    }


}
