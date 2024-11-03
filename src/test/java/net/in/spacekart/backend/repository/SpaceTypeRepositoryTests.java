package net.in.spacekart.backend.repository;


import com.cloudinary.Cloudinary;
import net.in.spacekart.backend.config.IndependentBeansConfig;
import net.in.spacekart.backend.database.MediaEntityListener;
import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.repositories.SpaceTypeRepository;
import net.in.spacekart.backend.services.CloudinaryService;
import net.in.spacekart.backend.services.impl.CloudinaryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({TestConfig.class, IndependentBeansConfig.class, Cloudinary.class,SpaceTypeRepository.class})
public class SpaceTypeRepositoryTests {

    @Autowired
    private SpaceTypeRepository spaceTypeRepository;




    @Test
    public void testFindById() {
        SpaceType spaceType = new SpaceType();

       Long id =  spaceTypeRepository.saveMy(spaceType);
        Assertions.assertNotNull(id);

    }


}


@TestConfiguration
 class TestConfig {

    public CloudinaryService cloudinaryService() {
        return  new CloudinaryServiceImpl();
    }

    @Bean
    public MediaEntityListener mediaEntityListener() {
        return new MediaEntityListener(cloudinaryService());
    }

}
