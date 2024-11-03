package net.in.spacekart.backend.database.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.ViewSpaceKart;
import net.in.spacekart.backend.database.MediaEntityListener;
import net.in.spacekart.backend.services.CloudinaryService;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(MediaEntityListener.class)
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Empty=images url cannot be stored")
    private String url;


    @Column(nullable = false)
    @NotBlank(message = "publicId of image cannot be null")
    private String publicId;


    @Column(nullable = false)
    @NotBlank(message = "assetId cannot be blank")
    private String assetId;

    public Media(Long id) {
        this.id = id;
    }

    public  Media(String url, String publicId, String assetId) {
        this.url = url;
        this.publicId = publicId;
        this.assetId = assetId;
    }


}
