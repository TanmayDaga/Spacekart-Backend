package net.in.spacekart.backend.controllers.apis.v1;

import jakarta.transaction.Transactional;
import net.in.spacekart.backend.payloads.spaceType.GuestSpaceTypeDto;
import net.in.spacekart.backend.services.entityServices.SpaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/spacetypes")
public class SpaceTypeController {


    private final SpaceTypeService spaceTypeService;

    @Autowired
    public SpaceTypeController(SpaceTypeService spaceTypeService) {
        this.spaceTypeService = spaceTypeService;
    }


    @GetMapping
    public ResponseEntity<List<GuestSpaceTypeDto>> findAllSpaceTypes() {
        return new ResponseEntity<List<GuestSpaceTypeDto>>(spaceTypeService.getAll(), HttpStatus.FOUND);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<String> saveSpaceType(@RequestBody GuestSpaceTypeDto spaceType) {

        spaceTypeService.save(spaceType);
        return new ResponseEntity<String>("submitted successfully", HttpStatus.CREATED);
    }


}
