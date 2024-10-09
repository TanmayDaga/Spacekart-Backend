package net.in.spacekart.backend.controllers;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestDto {


    String Name;
    @Min(18)
    Integer age;


    Boolean active;


    Address1 address;

    OffsetDateTime dateTime;
    MultipartFile file;

    class Address1 {
        public String Street;

        public Address1(String street) {
            this.Street = street;
        }

        public Address1() {
        }

        ;

    }

}
