package net.in.spacekart.backend.controllers.apis.v1;

import jakarta.transaction.Transactional;
import net.in.spacekart.backend.payloads.spaceType.GetSpaceTypeProjection;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.SpaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/spacetypes")
public class SpaceTypeController {


    private final SpaceTypeService spaceTypeService;
    private final UtilsService utilsService;

    @Autowired
    public SpaceTypeController(SpaceTypeService spaceTypeService, UtilsService utilsService) {
        this.spaceTypeService = spaceTypeService;
        this.utilsService = utilsService;
    }


    @GetMapping
    public ResponseEntity<List<GetSpaceTypeProjection>> findAllSpaceTypes(Authentication auth) {

        return new ResponseEntity<List<GetSpaceTypeProjection>>(spaceTypeService.getAll(), HttpStatus.FOUND);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<String> saveSpaceType(@RequestBody GetSpaceTypeProjection spaceType) {

        spaceTypeService.save(spaceType);
        return new ResponseEntity<String>("submitted successfully", HttpStatus.CREATED);
    }


}
