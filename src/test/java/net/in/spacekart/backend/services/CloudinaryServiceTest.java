package net.in.spacekart.backend.services;


import com.cloudinary.Cloudinary;

import net.in.spacekart.backend.BaseTestConfig;
import net.in.spacekart.backend.config.CloudinaryConfig;
import net.in.spacekart.backend.config.IndependentBeansConfig;
import net.in.spacekart.backend.database.MediaEntityListener;
import net.in.spacekart.backend.services.impl.CloudinaryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = { TestConfig.class, BaseTestConfig.class})
@ExtendWith(SpringExtension.class)
public class CloudinaryServiceTest {

    @Autowired
    CloudinaryService cloudinaryService;

    @Test
    public void testUpload(){

        ClassPathResource resource = new ClassPathResource("image.jpg");

        try {
            // Convert the resource file to byte array
            byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());
            Map result =  cloudinaryService.uploadFile(fileContent, "tests");
            Assertions.assertNotNull(result.get("public_id"),"public_id is null");
            Assertions.assertNotNull(result.get("url"),"url is null");
            Assertions.assertNotNull(result.get("asset_id"),"asset_id is null");
            assertTrue(result.get("url").toString().startsWith("http"), "URL should start with http");

        }
        catch (Exception e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

    }
}


@TestConfiguration
@Import(CloudinaryConfig.class)
class TestConfig{

    @Bean
    public  CloudinaryService cloudinaryService(){
        return  new CloudinaryServiceImpl();
    }
}
