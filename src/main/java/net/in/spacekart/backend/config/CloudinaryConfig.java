package net.in.spacekart.backend.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Autowired
    Dotenv dotenv;

    @Bean
    public Cloudinary cloudinary(){
        return  new Cloudinary(ObjectUtils.asMap("cloud_name",dotenv.get("CLOUDINARY_CLOUD_NAME")
        ,"api_secret",dotenv.get("CLOUDINARY_API_SECRET")
        ,"api_key",dotenv.get("CLOUDINARY_API_KEY")));
    }

}
