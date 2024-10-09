package net.in.spacekart.backend.database;


import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediaEntityListener {


    private final CloudinaryService cloudinaryService;

    @Autowired
    public MediaEntityListener( CloudinaryService cloudinaryService1) {

        this.cloudinaryService = cloudinaryService1;
    }


    @PreRemove
    public void onPreRemove(Media m) {
        cloudinaryService.deleteFileAsync(m.getPublicId());
    }

    @PreUpdate
    public void onPreUpdate(Media m) {
       cloudinaryService.deleteFileAsync(m.getPublicId());
    }


}
