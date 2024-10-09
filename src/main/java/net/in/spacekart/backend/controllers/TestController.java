package net.in.spacekart.backend.controllers;


import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class TestController {

    @Autowired
    Tika tika;


    @PostMapping("/test")
    public ResponseEntity<?> test(@Valid @ModelAttribute TestDto testDto) throws IOException {
        System.out.println(testDto.getAge());
        System.out.println(testDto.getName());
        System.out.println(testDto.getActive());
        System.out.println(testDto.dateTime);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
